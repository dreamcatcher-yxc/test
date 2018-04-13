package com.example.db.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class Demo01 {

    /**
     * 连接数据库, 不需要用户名和密码
     */
    @Test
    public void noUsernameAndPassword() {
        try{
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
            System.out.println("Connect to database successfully");
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    @Test
    public void needUsernameAndPassword() {
        try {
            List<ServerAddress> addrs = Arrays.asList(new ServerAddress("localhost", 27017));
            List<MongoCredential>  credentials = Arrays.asList(
                    MongoCredential.createScramSha1Credential("root", "db_test", "123456".toCharArray())
            );
            MongoClient client = new MongoClient(addrs, credentials);
            MongoDatabase mongoDatabase = client.getDatabase("db_test");
            System.out.println("Connect to database successfully");
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
