package com.pwl.shiro.controller;


import com.pwl.shiro.common.ResultVO;
import com.pwl.shiro.entity.SysUser;
import com.pwl.shiro.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pwl
 * @since 2019-06-20
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    //需要有systemUser权限才能访问
    @RequiresPermissions("systemUserAdd")
    @GetMapping
    public ResultVO getUserList(){
        List<SysUser> sysUsers = sysUserService.selectList(null);
        return new ResultVO(sysUsers);
    }

    //需要有add权限才能访问
    @RequiresPermissions("Add")
    @GetMapping("/getList")
    public ResultVO getList(){
        List<SysUser> sysUsers = sysUserService.selectList(null);
        return new ResultVO(sysUsers);
    }

}

