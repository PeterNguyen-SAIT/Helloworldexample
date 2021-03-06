package ca.sait;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EverSourceApplication {
    @GetMapping("/home")
    public String homepage() {
        return "customer/index";
    }

    public static void main(String[] args) {
        SpringApplication.run(EverSourceApplication.class, args);
    }

}
