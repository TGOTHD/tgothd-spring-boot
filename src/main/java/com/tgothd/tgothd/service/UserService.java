package com.tgothd.tgothd.service;

import com.github.pagehelper.PageInfo;
import com.tgothd.tgothd.entity.User;

import java.util.List;

/**
 * @author: ShrJanLan
 * @date: 2023/6/10 21:54
 * @description:
 */
public interface UserService {

    List<User> queryUserList();

    PageInfo<User> queryUserList(Integer pageNow, Integer pageSize);

    int save(User user);

    User findById(Integer id);

    int updateById(User user);

    int deleteById(Integer id);

}
