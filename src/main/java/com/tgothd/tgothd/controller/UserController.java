package com.tgothd.tgothd.controller;

import com.github.pagehelper.PageInfo;
import com.tgothd.tgothd.entity.User;
import com.tgothd.tgothd.service.UserService;
import com.tgothd.tgothd.utils.I18nUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: ShrJanLan
 * @date: 2023/6/10 21:55
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public List<User> list(){
        log.info("queryUserList");
        log.info(I18nUtil.get("index.title"));
        return userService.queryUserList();
    }

    @GetMapping("/pageInfo")
    public PageInfo<User> pageInfo(@RequestParam(value = "pageNow", defaultValue = "1") Integer pageNow
            , @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return userService.queryUserList(pageNow, pageSize);
    }

    @PostMapping("/save")
    public int save(@RequestBody User user) {
        try {
            return userService.save(user);
        } catch (Exception e) {
           return 0;
        }
    }

    @GetMapping("/findById")
    public User findById(@RequestParam(value = "id") Integer id) {
        return userService.findById(id);
    }

    @PutMapping("/update")
    public int update(@RequestBody User user) {
        try {
            return userService.updateById(user);
        } catch (Exception e) {
            return 0;
        }
    }

    @DeleteMapping("/deleteById")
    public int deleteById(@RequestParam(value = "id") Integer id) {
        try {
            return userService.deleteById(id);
        } catch (Exception e) {
            return 0;
        }
    }

}
