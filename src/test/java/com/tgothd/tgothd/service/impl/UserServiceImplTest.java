package com.tgothd.tgothd.service.impl;

import com.tgothd.tgothd.service.UserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: ShrJanLan
 * @date: 2023/6/11 16:57
 * @description:
 */
@SpringBootTest
class UserServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);

    @Autowired
    private UserService userService;

    @Test
    void queryUserList() {
        logger.info("queryUserList");
        System.out.println(userService.queryUserList());
    }

}