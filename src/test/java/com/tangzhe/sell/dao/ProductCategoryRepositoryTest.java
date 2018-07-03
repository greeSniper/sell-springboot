package com.tangzhe.sell.dao;

import com.tangzhe.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    ProductCategoryRepository repository;

    @Test
    public void findOneTest() {
        ProductCategory productCategory = repository.findOne(1);
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
        System.out.println(productCategory);
    }

    @Test
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory("热销榜", 1);
        ProductCategory result = repository.save(productCategory);
        //Assert.assertNotEquals(null, result);
        Assert.assertNotNull(result);
    }

    @Test
    public void updateTest() {
        ProductCategory productCategory = repository.findOne(3);
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(3);
        ProductCategory result = repository.save(productCategory);
        Assert.assertEquals(productCategory, result);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<ProductCategory> productCategoryList = repository.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4));
        Assert.assertNotEquals(0, productCategoryList.size());
    }

}
