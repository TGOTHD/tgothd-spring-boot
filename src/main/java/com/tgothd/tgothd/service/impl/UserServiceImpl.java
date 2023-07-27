package com.tgothd.tgothd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tgothd.tgothd.entity.User;
import com.tgothd.tgothd.mapper.UserMapper;
import com.tgothd.tgothd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: ShrJanLan
 * @date: 2023/6/10 21:54
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> queryUserList(){
        return userMapper.queryUserList();
    }

    @Override
    public PageInfo<User> queryUserList(Integer pageNow, Integer pageSize) {
        PageHelper.startPage(pageNow, pageSize);
        List<User> userList = userMapper.queryUserList();
        return new PageInfo<>(userList);
    }

    @Override
    public int save(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateById(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteById(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

}
