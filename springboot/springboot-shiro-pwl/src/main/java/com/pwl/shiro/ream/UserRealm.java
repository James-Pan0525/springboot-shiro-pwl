package com.pwl.shiro.ream;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.pwl.shiro.entity.SysUser;
import com.pwl.shiro.service.SysPermissionService;
import com.pwl.shiro.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pan Weilong
 * @date 2019/6/20 20:11
 * @description: 接口.
 */
public class UserRealm extends AuthorizingRealm{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();
        //List<String> sysPermissions = sysPermissionService.selectPermissionByUserId(sysUser.getUserId());
        List<String> sysPermissions=new ArrayList<>();
        sysPermissions.add("systemUserAdd");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(sysPermissions);
        LOGGER.info("doGetAuthorizationInfo");
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Wrapper<SysUser> objectWrapper = new EntityWrapper<>();
        objectWrapper.eq("user_name",token.getUsername());
        SysUser sysUser = sysUserService.selectOne(objectWrapper);
        if (sysUser == null) {
            return null;
        }
        LOGGER.info("doGetAuthenticationInfo");
        return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword().toCharArray(), ByteSource.Util.bytes(sysUser.getSalt()), getName());
    }
}
