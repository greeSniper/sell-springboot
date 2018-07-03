package com.tangzhe.sell.service;

import com.tangzhe.sell.dao.ProductInfoRepository;
import com.tangzhe.sell.dto.CartDTO;
import com.tangzhe.sell.entity.ProductInfo;
import com.tangzhe.sell.enums.ProductStatusEnum;
import com.tangzhe.sell.enums.ResultEnum;
import com.tangzhe.sell.exception.SellException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    /**
     * 通过id查询
     */
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    /**
     * 查询所有在架商品
     */
    public List<ProductInfo> findAllUp() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    /**
     * 分页查询所有商品
     */
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * 添加商品
     */
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    /**
     * 加库存
     */
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if(productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    /**
     * 减库存
     */
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if(productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        return null;
    }

    @Override
    public ProductInfo offSale(String productId) {
        return null;
    }

}
