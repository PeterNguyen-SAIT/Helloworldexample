package ca.sait.dao;

import ca.sait.entity.OrdersEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * This is the OrderDao to handle all the operation with database.
 * @author bin
 * @email bin.zhang@sait.ca
 * @date 2020-07-08 23:13:21
 */
@Mapper
public interface OrdersDao extends BaseMapper<OrdersEntity> {
	
}
