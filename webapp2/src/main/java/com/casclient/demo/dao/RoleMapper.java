package com.casclient.demo.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.ibatis.annotations.Param;

import com.casclient.demo.entity.Role;
import com.casclient.demo.entity.vo.RoleVo;
import com.casclient.demo.entity.Resource;

/**
 *
 * Role 表数据库控制层接口
 *
 */
public interface RoleMapper{
    Set<String> findRoleByUserId(Long id);
    Role findRoleByRoleId(Long id);
    RoleVo selectRoleVoByUserId(Long id);
    List<Resource> selectResourceListByRoleIdList(@Param("list") List<Long> list);
    List<Long> selectResourceIdListByRoleId(@Param("id") Long id);

    List<Map<Long, String>> selectResourceListByRoleId(@Param("id") Long id); 

}
