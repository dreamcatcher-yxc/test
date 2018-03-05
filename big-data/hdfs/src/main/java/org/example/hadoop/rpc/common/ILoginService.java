package org.example.hadoop.rpc.common;

public interface ILoginService {
    long versionID = 1L;

    String login(String username, String password);
}
