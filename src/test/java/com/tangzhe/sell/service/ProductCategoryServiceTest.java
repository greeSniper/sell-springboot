package com.tangzhe.sell.service;

import com.tangzhe.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceTest {

    @Autowired
    private ProductCategoryService service;

    @Test
    public void findAllTest() {
        List<ProductCategory> result = service.findAll();
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void saveTest() {
        ProductCategory result = service.save(new ProductCategory("专享优惠", 4));
        Assert.assertNotNull(result);
    }

}
