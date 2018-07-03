package com.tangzhe.sell.service;

import com.tangzhe.sell.dao.ProductCategoryRepository;
import com.tangzhe.sell.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    /**
     * 通过id查询
     */
    public ProductCategory findOne(int categoryId) {
        return repository.findOne(categoryId);
    }

    /**
     * 查询所有
     */
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    /**
     * 通过类目类型查询
     */
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    /**
     * 添加类目
     */
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

}
