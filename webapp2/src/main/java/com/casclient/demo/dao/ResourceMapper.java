package com.casclient.demo.dao;
import java.util.List;

import com.casclient.demo.entity.Resource;

/**
 *
 * Resource 表数据库控制层接口
 *
 */
public interface ResourceMapper {

    List<Resource> selectResourceListByRoleId(Long id);
}
