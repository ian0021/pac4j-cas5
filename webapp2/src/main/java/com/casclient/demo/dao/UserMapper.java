package com.casclient.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.casclient.demo.entity.User;
import com.casclient.demo.entity.vo.UserVo;

/**
 *
 * User 表数据库控制层接口
 *
 */
public interface UserMapper{

    User selectByUserName(String username);
    List<User> selectAlluser();
}
