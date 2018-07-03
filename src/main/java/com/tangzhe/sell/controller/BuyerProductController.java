package com.tangzhe.sell.controller;

import com.tangzhe.sell.entity.ProductCategory;
import com.tangzhe.sell.entity.ProductInfo;
import com.tangzhe.sell.service.ProductCategoryService;
import com.tangzhe.sell.service.ProductInfoService;
import com.tangzhe.sell.utils.ResultVOUtil;
import com.tangzhe.sell.vo.ProductInfoVO;
import com.tangzhe.sell.vo.ProductVo;
import com.tangzhe.sell.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 */
@RestController
@RequestMapping("buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 商品列表
     */
    @GetMapping("/list")
    public ResultVo<ProductVo> list() {

        //1. 查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findAllUp();

        //2. 查询上架商品的类目
        //传统方法
//        List<Integer> categoryTypeList = new ArrayList<>();
//        for (ProductInfo productInfo : productInfoList) {
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        //精简方法(java8, lambda)
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        //3. 分装数据
        List<ProductVo> productVoList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if(productCategory.getCategoryType().equals(productInfo.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVo.setProductInfoVOList(productInfoVOList);
            productVoList.add(productVo);
        }

        return ResultVOUtil.success(productVoList);
    }

}
