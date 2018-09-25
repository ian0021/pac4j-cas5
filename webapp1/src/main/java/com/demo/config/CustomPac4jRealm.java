package com.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import io.buji.pac4j.realm.Pac4jRealm;

public class CustomPac4jRealm extends Pac4jRealm{
	private final static String SELFAUTHZ = "user:edit";
	
	private final static Logger LOGGER = LoggerFactory.getLogger(CustomPac4jRealm.class);
	
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authenticationToken)
            throws AuthenticationException {

        return super.doGetAuthenticationInfo(authenticationToken);
    }

    /**
	 * 由于使用了SSO单点登录系统，此Ream只负责授权
	 * 权限认证（为当前登录的Subject授予角色和权限）
	 * 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
		SimpleAuthorizationInfo info=null ;
		try{

			       // 权限信息对象info，用来存放查出的用户的所有的角色及权限
			       info = new SimpleAuthorizationInfo();
				   List<String> permissions = new ArrayList<String>();
				   permissions.add(SELFAUTHZ);
				   LOGGER.info("========================权限permission----------> {}",JSON.toJSONString(SELFAUTHZ));
				   info.addStringPermissions(permissions);


		}catch(Exception e){
			LOGGER.info("=====================ShiroCasRealm异常---------------");
		}
		return info;
	}
}
