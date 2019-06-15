package com.example.netty.demo06;

import org.eclipse.jetty.npn.NextProtoNego;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultServerProvider implements NextProtoNego.ServerProvider {
    // 定义所有的 ServerProvider 实现的协议
    private static final List<String> PROTOCOLS = Collections.unmodifiableList(Arrays.asList("spdy/2", "spdy/3", "http/1.1"));  //1

    private String protocol;

    @Override
    public void unsupported() {
        // 设置如果 SPDY 协议失败了就转到 http/1.1
        protocol = "http/1.1";   //2
    }

    @Override
    public List<String> protocols() {
        // 返回支持的协议的列表
        return PROTOCOLS;   //3
    }

    @Override
    public void protocolSelected(String protocol) {
        // 设置选择的协议
        this.protocol = protocol;  //4
    }

    public String getSelectedProtocol() {
        // 返回选择的协议
        return protocol;  //5
    }
}