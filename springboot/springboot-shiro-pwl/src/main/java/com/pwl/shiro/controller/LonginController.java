package com.pwl.shiro.controller;

import com.pwl.shiro.common.ResultVO;
import com.pwl.shiro.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pan Weilong
 * @date 2019/6/20 21:00
 * @description: 接口.
 */
@RestController
public class LonginController {

    @GetMapping("/login")
    public ResultVO login(HttpServletRequest request){
        return new ResultVO().returnFail(401,"认证失败");
    }

    @PostMapping("/login")
    public ResultVO login(@RequestBody SysUser sysUser) {
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUserName(), sysUser.getPassword());
        try {
            //shiro帮我们匹配密码什么的，我们只需要把东西传给它，它会根据我们在UserRealm里认证方法设置的来验证
            user.login(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthenticatedException();
        }
        return new ResultVO("登录成功");
    }
}
