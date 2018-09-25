package com.casclient.demo.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.casclient.demo.entity.User;
import com.casclient.demo.service.RoleService;
import com.casclient.demo.service.UserService;


import io.buji.pac4j.realm.Pac4jRealm;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.cas.profile.CasProfile;
import io.buji.pac4j.subject.Pac4jPrincipal;

public class CustomPac4jRealm extends Pac4jRealm{
	private final static Logger LOGGER = LoggerFactory.getLogger(CustomPac4jRealm.class);

    @Autowired
    private UserService userService;
    @Autowired
	private RoleService roleService;
	
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authenticationToken)
            throws AuthenticationException {

        return super.doGetAuthenticationInfo(authenticationToken);
    }

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
	   List<CommonProfile> profiles = null;
		final Pac4jPrincipal principal = principals.oneByType(Pac4jPrincipal.class);
        if (principal != null) {
            profiles = principal.getProfiles();
        }

		String userName = null;
        CasProfile casProfile = (CasProfile)profiles.get(0);	
        userName = casProfile.getId();

		SimpleAuthorizationInfo info=null ;
		try{

			       info = new SimpleAuthorizationInfo();

                    User user = userService.selectByUserName(userName);

                    Map<String, Set<String>> resourceMap = roleService.selectResourceMapByUserId(user.getId());
                    Set<String> permissions = resourceMap.get("permissions");
                    Set<String> roles = resourceMap.get("roles");


                    info.setRoles(roles);
                    info.setStringPermissions(permissions);

		}catch(Exception e){
			LOGGER.info("=====================ShiroCasRealm异常---------------");
		}
		return info;
	}
}
