package ca.sait.controllers;

import ca.sait.entity.OrdersEntity;
import ca.sait.entity.ProductsEntity;
import ca.sait.service.impl.OrdersServiceImpl;
import ca.sait.service.impl.ProductsServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ca.sait.entity.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page; //0719

/**
 * Controller class for customer/product.html
 * @author Peter Nguyen, Melody Zhang
 */
@Controller
public class VegetableController {

    @Autowired
    private ProductsServiceImpl productsService;

    @Autowired
    private OrdersServiceImpl ordersService;

    /**
     * Limits each page to 6 products.
     * Will display all products but will need to login/register to add to cart.
     * @param model
     * @param session
     * @param page
     * @param limit
     * @return product.html
     */
    @GetMapping("/product")
    public String showForm(Model model, HttpSession session, Long page,
                           Long limit) { //0719
        String username = (String) session.getAttribute("username");
        List<ProductsEntity> productsEntityList = productsService.list();
        model.addAttribute("productsEntityList", productsEntityList);
        model.addAttribute("loggedIn", " " + username);
        model.addAttribute("usernameExist", username);
        //0719-------------
        if (page == null) {
            page = 1L;
        }
        limit = 6L;
        Page<ProductsEntity> entityPage = productsService.page(new Page<>(page, limit));
        model.addAttribute("entityPage", entityPage);
        entityPage.getPages();

        return "customer/product";
    }

    /**
     * Will add to cart the product to a cart.
     * Checks for username stored in session before add to cart.
     * Checks for quantity for products before displaying.
     * If successful new order will be created and add to the cart session.
     * @param productSource
     * @param productQuantity
     * @param model
     * @param session
     * @param page
     * @param limit
     * @return product.html
     */
    @PostMapping("/productId")
    public String submitForm(@RequestParam int productSource, @RequestParam int productQuantity, Model model, HttpSession session, Long page,
                             Long limit) {
        String username = (String) session.getAttribute("username");
        if (username == null || username.equals("")) {
            model.addAttribute("message", "Please login first");
            model.addAttribute("usersEntity", new UsersEntity());
            return "customer/login";
        } else {

            ProductsEntity productsEntity = productsService.getById(productSource);

            if (productQuantity > productsEntity.getStock()) {
                model.addAttribute("message", "Amount exceeds stock. Stock: " + productsEntity.getStock() + " " + productsEntity.getUnit());
            } else {
                OrdersEntity oldOrder = null;
                List<OrdersEntity> allOrders = ordersService.list();
                boolean productExist = false;

                for (OrdersEntity order : allOrders) {
                    if (productsEntity.getPname().equals(order.getPname()) && username.equals(order.getUname())) {
                        productExist = true;
                        oldOrder = order;
                        break;
                    }
                }
                if (productExist) {
                    int currentQuantity = oldOrder.getQuantity();
                    int newQuantity = currentQuantity + productQuantity;
                    oldOrder.setQuantity(newQuantity);
                    if (oldOrder.getImage() != null) {

                    } else {
                        QueryWrapper<ProductsEntity> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("pname", oldOrder.getPname());
                        ProductsEntity product = productsService.getOne(queryWrapper);
                        oldOrder.setImage(product.getImage());
                    }
                    if (ordersService.saveOrUpdate(oldOrder)) {
                        model.addAttribute("message", "Added " + productQuantity + " " + productsEntity.getUnit() + " of " + productsEntity.getPname() + " to cart." + "\n" + "Current amount in cart: " + newQuantity + " " + productsEntity.getUnit());

                    } else {
                        model.addAttribute("message", "Failed to add, please contact developers for assistance");
                    }
                } else {
                    int newOID = allOrders.size() + 1;

                    OrdersEntity newOrder = new OrdersEntity();
                    newOrder.setUnit(productsEntity.getUnit());
                    newOrder.setQuantity(productQuantity);
                    newOrder.setUname(username);
                    newOrder.setPname(productsEntity.getPname());
                    newOrder.setPrice(productsEntity.getPrice());
                    newOrder.setStatus("hold");
                    boolean inOrder = true;
                    for (int i = 1; i <= allOrders.size(); i++) {
                        if (i != allOrders.get(i - 1).getOid()) {
                            newOrder.setOid(i);
                            inOrder = false;
                            break;
                        }
                    }

                    if (inOrder) {
                        newOrder.setOid(newOID);
                    }

                    if (ordersService.save(newOrder)) {
                        model.addAttribute("message", "Added " + productQuantity + " " + productsEntity.getUnit() + " of " + productsEntity.getPname() + " to cart." + "\n" + "Current amount in cart: " + productQuantity + " " + productsEntity.getUnit());

                    } else {
                        model.addAttribute("message", "Failed to add, please contact developers for assistance");
                    }
                }
            }
            List<ProductsEntity> productsEntityList = productsService.list();
            model.addAttribute("productsEntityList", productsEntityList);
            model.addAttribute("loggedIn", " " + username);
            model.addAttribute("usernameExist", " " + username);
            if (page == null) {
                page = 1L;
            }
            limit = 6L;
            //0727 to show currentPage

            int tempPage = productSource / limit.intValue();
            int mod = productSource % limit.intValue();

            int currentPage = (mod == 0) ? tempPage : tempPage + 1;

            page = new Long(currentPage);

            Page<ProductsEntity> entityPage = productsService.page(new Page<>(page, limit));
            model.addAttribute("entityPage", entityPage);
            entityPage.getPages();
            return "customer/product";
        }

    }

    /**
     * Redirects to history.html saving username session attribute.
     * @param model
     * @param session
     * @return history.html
     */

    @GetMapping("/history")
    public String showHistoryPage(ModelMap model, HttpSession session) {
        model.addAttribute("loggedIn", " " + session.getAttribute("username"));
        model.addAttribute("usernameExist", " " + session.getAttribute("username"));
        return "customer/history";
    }
}
