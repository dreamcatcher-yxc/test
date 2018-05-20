package com.example.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class MyPropertiesRealm implements Realm {

    private Properties properties;

    public MyPropertiesRealm() throws IOException {
        this.properties = new Properties();
        InputStream usersIn = MyPropertiesRealm.class.getClassLoader().getResourceAsStream("users.properties");
        this.properties.load(usersIn);
    }

    /**
     * 返回本 Realm 的名称。
     * @return
     */
    @Override
    public String getName() {
        return "MyPropertiesRealm";
    }

    /**
     * 判断传入的 Token 类型是否支持。
     * @param authenticationToken
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        // 仅支持 UsernamePasswordToken 类型的 token
        return authenticationToken instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password =  new String((char[])authenticationToken.getCredentials());
        Set<Object> keySet = this.properties.keySet();

        for(Object key : keySet) {
            if(username.equals(key)) {
                if(password.equals(this.properties.get(key))) {
                    // 验证成功，返回一个 Authentication 实现。
                    return new SimpleAuthenticationInfo(username + "@163.com", password, getName());
                } else {
                    // 用户名或者密码错误
                    throw new IncorrectCredentialsException();
                }
            }
        }
        // 目标账户不存在
        throw new UnknownAccountException();
    }
}
