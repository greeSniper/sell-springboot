package com.tangzhe.sell.service;

import com.tangzhe.sell.dto.CartDTO;
import com.tangzhe.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    ProductInfo findOne(String productId);

    /** 查询所有在架商品 */
    List<ProductInfo> findAllUp();

    /** 分页查询所有商品 */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    /** 加库存 */
    void increaseStock(List<CartDTO> cartDTOList);

    /** 减库存 */
    void decreaseStock(List<CartDTO> cartDTOList);

    /** 上架 */
    ProductInfo onSale(String productId);

    /** 下架 */
    ProductInfo offSale(String productId);

}
