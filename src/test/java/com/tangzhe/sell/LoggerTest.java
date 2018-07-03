package com.tangzhe.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j    //需下载lombok插件
public class LoggerTest {

    //private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test01() {
        log.debug("debug...");
        log.info("info...");
        log.error("error...");

        String name = "root";
        String password = "123456";
        log.info("name：" + name + ", password：" + password);
        log.info("name：{}, password：{}", name, password);
    }

}
