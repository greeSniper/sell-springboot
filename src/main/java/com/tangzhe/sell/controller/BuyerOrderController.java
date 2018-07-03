package com.tangzhe.sell.controller;

import com.tangzhe.sell.converter.OrderForm2OrderDTOConverter;
import com.tangzhe.sell.dto.OrderDTO;
import com.tangzhe.sell.enums.ResultEnum;
import com.tangzhe.sell.exception.SellException;
import com.tangzhe.sell.form.OrderForm;
import com.tangzhe.sell.service.OrderService;
import com.tangzhe.sell.utils.ResultVOUtil;
import com.tangzhe.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 唐哲
 * 2017-11-22 13:25
 */
@RestController
@RequestMapping("buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;
    
    /**
     * 创建订单
     */
    @PostMapping("/create")
    public ResultVo<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        //校验请求参数
        if(bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                                    bindingResult.getFieldError().getDefaultMessage());
        }

        //判断购物车是否为空
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        //创建订单
        OrderDTO createResult = orderService.create(orderDTO);

        //封装返回参数
        Map<String, String> data = new HashMap<>();
        data.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success(data);
    }

    /**
     * 订单列表
     */
    @GetMapping("/list")
    public ResultVo<List<OrderDTO>> list(@RequestParam String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "page", defaultValue = "10") Integer size) {
        if(StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    /**
     * 订单详情
     */
    @GetMapping("/detail")
    public ResultVo<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        if(StringUtils.isEmpty(openid)) {
            log.error("【查询订单详情】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if(StringUtils.isEmpty(orderId)) {
            log.error("【查询订单详情】orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null) {
            return null;
        }
        if(!openid.equalsIgnoreCase(orderDTO.getBuyerOpenid())) {
            log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }

        return ResultVOUtil.success(orderDTO);
    }

    /**
     * 取消订单
     */
    @PostMapping("cancel")
    public ResultVo cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        if(StringUtils.isEmpty(openid)) {
            log.error("【取消订单】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if(StringUtils.isEmpty(orderId)) {
            log.error("【取消订单】orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null) {
            log.error("【取消订单】查不到改订单, orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if(!openid.equalsIgnoreCase(orderDTO.getBuyerOpenid())) {
            log.error("【取消订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }

        orderService.cancel(orderDTO);

        return ResultVOUtil.success();
    }

}
