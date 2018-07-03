package com.tangzhe.sell.service;

import com.tangzhe.sell.entity.ProductInfo;
import com.tangzhe.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceTest {

    @Autowired
    private ProductInfoService service;

    @Test
    public void findOneTest() {
        ProductInfo productInfo = service.findOne("123456");
        Assert.assertEquals("123456", productInfo.getProductId());
    }

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(8.8));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好吃的虾");
        productInfo.setProductIcon("http://yyyyy.jpg");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(2);

        ProductInfo result = service.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findAllUpTest() {
        List<ProductInfo> productInfoList = service.findAllUp();
        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void findAllTest() {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> productInfoPage = service.findAll(pageRequest);
        Assert.assertNotEquals(0, productInfoPage.getTotalElements());
    }

}
