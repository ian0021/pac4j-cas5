package com.casclient.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.casclient.demo.entity.User;
import com.casclient.demo.entity.vo.UserVo;
import com.casclient.demo.service.PasswordHelper;
import com.casclient.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.casclient.demo.dao.UserMapper;
import com.casclient.demo.dao.UserRoleMapper;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PasswordHelper passwordHelper;


    @Override
    public User selectByUserName(String userName){
        return userMapper.selectByUserName(userName);
    }

    
    @Override
    public List<User> selectAlluser() {
        return userMapper.selectAlluser();
    }
}
