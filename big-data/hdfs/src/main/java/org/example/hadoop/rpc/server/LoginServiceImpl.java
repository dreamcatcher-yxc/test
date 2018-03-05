package org.example.hadoop.rpc.server;

import org.example.hadoop.rpc.common.ILoginService;

public class LoginServiceImpl implements ILoginService {
    public String login(String username, String password) {
        return String.format("%s login successful!!!", username);
    }
}
