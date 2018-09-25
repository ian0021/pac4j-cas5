package com.casclient.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.casclient.demo.entity.Role;

/**
 *
 * Role 表数据服务层接口
 *
 */
public interface RoleService {

    List<Long> selectResourceIdListByRoleId(Long id);

    Map<String, Set<String>> selectResourceMapByUserId(Long userId);
}
