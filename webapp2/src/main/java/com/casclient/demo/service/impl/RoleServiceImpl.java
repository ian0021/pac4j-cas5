package com.casclient.demo.service.impl;

import com.casclient.demo.dao.RoleMapper;
import com.casclient.demo.dao.RoleResourceMapper;
import com.casclient.demo.dao.UserRoleMapper;
import com.casclient.demo.entity.Role;
import com.casclient.demo.entity.RoleResource;
import com.casclient.demo.service.RoleService;
import com.casclient.demo.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * Role 表数据服务层接口实现类
 *
 */
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    
    @Override
    public List<Long> selectResourceIdListByRoleId(Long id) {
        return roleMapper.selectResourceIdListByRoleId(id);
    }
    
    @Override
    public Map<String, Set<String>> selectResourceMapByUserId(Long userId) {
        Map<String, Set<String>> resourceMap = new HashMap<String, Set<String>>();
        List<Long> roleIdList = userRoleMapper.selectRoleIdListByUserId(userId);
        Set<String> urlSet = new HashSet<String>();
        Set<String> roles = new HashSet<String>();
        for (Long roleId : roleIdList) {
            List<Map<Long, String>> resourceList = roleMapper.selectResourceListByRoleId(roleId);
            if (resourceList != null && !resourceList.isEmpty()) {
                for (Map<Long, String> map : resourceList) {
                    if (map != null && StringUtils.isNotBlank(map.get("permission"))) {
                        urlSet.add(map.get("permission"));
                    }
                }
            }

            Role role = roleMapper.findRoleByRoleId(roleId);
            if (role != null) {
                roles.add(role.getRole());
            }
        }
        resourceMap.put("permissions", urlSet);
        resourceMap.put("roles", roles);
        return resourceMap;
    }

}
