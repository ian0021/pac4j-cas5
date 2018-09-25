package com.casclient.demo.service;

import java.util.List;

import com.casclient.demo.entity.User;
import com.casclient.demo.entity.vo.UserVo;

/**
 *
 * User 表数据服务层接口
 *
 */
public interface UserService {

    User selectByUserName(String userName);
    List<User> selectAlluser();
}
