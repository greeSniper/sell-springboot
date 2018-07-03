package com.tangzhe.sell.dao;

import com.tangzhe.sell.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 唐哲
 * 2017-11-20 16:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1234567811");
        orderDetail.setOrderId("1234567");
        orderDetail.setProductIcon("http://xxxxx.jpg");
        orderDetail.setProductId("11111112");
        orderDetail.setProductName("鸡蛋汤");
        orderDetail.setProductPrice(new BigDecimal(3.2));
        orderDetail.setProductQuantity(3);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> orderDetailList = repository.findByOrderId("1234567");
        Assert.assertNotEquals(0, orderDetailList.size());
    }

}
