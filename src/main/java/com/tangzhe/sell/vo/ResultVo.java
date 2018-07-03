package com.tangzhe.sell.vo;

import lombok.Data;

import java.util.List;

/**
 * http请求返回的最外层对象
 */
@Data
public class ResultVo <T> {

    private Integer code;
    private String msg;
    private T data;

}
