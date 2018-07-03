package com.tangzhe.sell.service;

import com.tangzhe.sell.dto.OrderDTO;
import com.tangzhe.sell.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 唐哲
 * 2017-11-20 18:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    private final String BUYER_OPENID = "1101110";
    private final String ORDER_ID = "1511175024237116258";

    @Test
    public void createTest() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("唐哲");
        orderDTO.setBuyerAddress("码码在线");
        orderDTO.setBuyerPhone("18852937197");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123456");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123457");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOneTest() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】result={}", orderDTO);
        Assert.assertEquals(ORDER_ID, orderDTO.getOrderId());
    }

    @Test
    public void findListTest() {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, pageRequest);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    public void cancelTest() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(new Integer(2), result.getOrderStatus());
    }

    @Test
    public void finishTest() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(new Integer(1), result.getOrderStatus());
    }

    @Test
    public void paidTest() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(new Integer(1), result.getPayStatus());
    }

    @Test
    public void findListTest2() {
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

}
