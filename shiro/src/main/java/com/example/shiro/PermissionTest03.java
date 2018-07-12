package com.example.shiro;

import org.apache.shiro.authz.UnauthorizedException;
import org.junit.Assert;
import org.junit.Test;

public class PermissionTest03 extends BaseTest {

    @Test
    public void testIsPermitted() {
        login("classpath:shiro-permission.ini", "zhang", "123");
        //判断拥有权限：user:create
        Assert.assertTrue(subject().isPermitted("user:create"));
        //判断拥有权限：user:update and user:delete
        Assert.assertTrue(subject().isPermittedAll("user:update", "user:delete"));
        //判断没有权限：user:view
        Assert.assertFalse(subject().isPermitted("user:view"));
    }

    @Test(expected = UnauthorizedException.class)
    public void testCheckPermission01 () {
        login("classpath:shiro-permission.ini", "zhang", "123");
        //断言拥有权限：user:create
        subject().checkPermission("user:create");
        //断言拥有权限：user:delete and user:update
        subject().checkPermissions("user:delete", "user:update");
        //断言拥有权限：user:view 失败抛出异常
        subject().checkPermissions("user:view");
    }

    @Test
    public void testCheckPermission02 () {
        login("classpath:shiro-permission.ini", "zhang", "123");
        subject().checkPermissions("system:user:update", "system:user:delete");
        // * 代表的是通配符.
        subject().checkPermissions("system:user:*");
        subject().checkPermissions("system:user:aaa");
        subject().checkPermissions("system:user");
    }

    @Test
    public void testCheckPermission03 () {
        login("classpath:shiro-permission.ini", "li", "123");
        subject().checkPermissions("user:view", "system:user:view");
//        subject().checkPermissions("system:user:update");
    }
}
