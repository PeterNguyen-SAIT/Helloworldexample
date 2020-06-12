package com.example2.service;

import ca.sait.common.utils.PageUtils;
import ca.sait.entity.OrderDetailsEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 
 *
 * @author bin
 * @email bin.zhang@sait.ca
 * @date 2020-06-08 17:39:53
 */
public interface OrderDetailsService extends IService<OrderDetailsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

