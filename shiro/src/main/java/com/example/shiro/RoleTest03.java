package com.example.shiro;

import org.apache.shiro.authz.UnauthorizedException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class RoleTest03 extends BaseTest {

    /**
     * 此种判断用户拥有的操作权限的工作算是一种隐式的权限判断, 为什么呢? 判断用户是否拥有某个权限直接使用角色来代替，将这些
     * 判断逻辑直接写在了代码中, 如果在将来中需要修改某个用户是否拥有某个权限的时候需要修改代码，故叫做隐式角色。
     */
    @Test
    public void testHasRoleAndHasRoles() {
        login("classpath:shiro-role.ini", "zhang", "123");
        Assert.assertTrue(subject().hasRole("role1"));
        Assert.assertTrue(subject().hasRole("role2"));
        Assert.assertFalse(subject().hasRole("role3"));

        boolean[] rs = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertTrue(rs[0]);
        Assert.assertTrue(rs[1]);
        Assert.assertFalse(rs[2]);
    }

    /**
     * 隐式角色.
     */
    @Test(expected = UnauthorizedException.class)
    public void testCheckRole() {
        login("classpath:shiro-role.ini", "zhang", "123");
        //断言拥有角色：role1
        subject().checkRole("role1");
        //断言拥有角色：role1 and role3 失败抛出异常
        subject().checkRoles("role1", "role3");
    }
}
