package com.example.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.junit.Test;

public class EntryptionTests {

    @Test
    public void test01() {
        String str = "hello";
        String salt = "123";
        int count = 2;

        // base64 编码
        String base64EncodeStr = Base64.encodeToString(str.getBytes());
        System.out.println(String.format("BASE64 encode string [%s] result is [%s]", str, base64EncodeStr));
        // base64 解码
//        String base64DecodeStr = new String(Hex.decode(base64EncodeStr.getBytes()));
//        System.out.println(String.format("BASE64 decode string [%s] result is [%s]", str, base64DecodeStr));
    }

}
