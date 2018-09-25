package com.casclient.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import com.casclient.demo.entity.UserRole;

/**
 *
 * UserRole 表数据库控制层接口
 *
 */
public interface UserRoleMapper{

    List<UserRole> selectByUserId(@Param("userId") Long userId);

    List<Long> selectRoleIdListByUserId(@Param("userId") Long userId);

}
