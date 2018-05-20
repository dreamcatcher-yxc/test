package com.example.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class LoginLogoutTest01 {

    /**
     * 基础校验处理
     */
    @Test
    public void testHelloWorld() {
        //1、获取 SecurityManager 工厂，此处使用 Ini 配置文件初始化 SecurityManager
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro.ini");
        //2、得到 SecurityManager 实例 并绑定给 SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到 Subject 及创建用户名/密码身份验证 Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        try {
        //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
        //5、身份验证失败
        }
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
        //6、退出
        subject.logout();
    }

    /**
     * 自定义 realm 校验
     */
    @Test
    public void testCustomRealm() {
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
        }
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
        subject.logout();
    }

    /**
     * 数据库校验测试
     */
    @Test
    public void testJdbcRealm() {
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "1234567");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
        }
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
        subject.logout();
    }

    /**
     * 测试 Authentication Strategy.
     * 配置要求校验规则如下:
     *      allSuccessfulStrategy=org.apache.shiro.authc.pam.AllSuccessfulStrategy
     *      securityManager.authenticator.authenticationStrategy=$allSuccessfulStrategy
     * 解析:
     *      要求所有配置的 realm 校验都通过才算校验通过.
     * 注意事项:
     *      这里配置的多个 realm 之间应该返回的 Authentication 信息中用户名不应该相同，否则会被认为是同一个  principals.
     */
    @Test
    public void testAuthenticationStrategy() {
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro-authenticator-all-success.ini");
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(2, subject.getPrincipals().asList().size()); //断言用户已经登录
        subject.logout();
    }
}
