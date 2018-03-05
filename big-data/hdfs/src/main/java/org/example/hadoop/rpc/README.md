# 1. RPC 步骤:
1.1 统一公共接口, 接口所在的包名字必须相同, 不许包含 versionID 字段。
 ```
 package org.example.hadoop.rpc.common;
 
 public interface ILoginService {
     long versionID = 1L;
 
     String login(String username, String password);
 }

```
1.2 server 包下包含接口的实现类，和 RPC 服务启动入口。
```
// LoginServiceImpl
package org.example.hadoop.rpc.server;

import org.example.hadoop.rpc.common.ILoginService;

public class LoginServiceImpl implements ILoginService {
    public String login(String username, String password) {
        return String.format("%s login successful!!!", username);
    }
}

// Main
package org.example.hadoop.rpc.server;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.RPC.Server;
import org.example.hadoop.rpc.common.ILoginService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Builder builder = new RPC.Builder(new Configuration());
        builder.setBindAddress("localhost")
                .setPort(9096)
                .setProtocol(ILoginService.class)
                .setInstance(new LoginServiceImpl());
        Server server = builder.build();
        server.start();
    }
}
```
1.3 client 包下是客户端 RPC 调用示例
```
package org.example.hadoop.rpc.client;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.example.hadoop.rpc.common.ILoginService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException{
        ILoginService proxy = RPC.getProxy(ILoginService.class, 1L, new InetSocketAddress("localhost", 9096), new Configuration());
        String result = proxy.login("mijie", "123456");
        System.out.println(result);
    }
}
```
   