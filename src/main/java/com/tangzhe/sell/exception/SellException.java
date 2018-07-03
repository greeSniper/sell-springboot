package com.tangzhe.sell.exception;

import com.tangzhe.sell.enums.ResultEnum;
import lombok.Getter;

/**
 * Created by 唐哲
 * 2017-11-20 16:45
 */
@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
