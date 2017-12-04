package org.example.groovy

import groovy.sql.Sql

/**
 * @DATE 2017/12/1 13:04
 * @author yangxiuchu
 */
class DbDemo {
    private static SQL;
    static  {
        SQL = Sql.newInstance('jdbc:mysql://localhost:3306/test?useSSL=false', 'root', '123456', 'com.mysql.jdbc.Driver');
    }

    static void simpleTest() {
        // 创建数据库连接.
        SQL.eachRow('SELECT VERSION()'){ row ->
            println row[0]
        }
        SQL.close()
    }

    static void createTableTest() {
        def sqlStr = """CREATE TABLE EMPLOYEE (
         FIRST_NAME CHAR(20) NOT NULL,
         LAST_NAME CHAR(20),
         AGE INT,
         SEX CHAR(1),
         INCOME FLOAT )"""
        SQL.execute(sqlStr);
        SQL.close()
    }

    static void insertValTest() {
        SQL.connection.autoCommit = false;
        def username = 'wangwu';
        def password = '123456';
        try {
            SQL.execute("insert into user(username, password) values($username, $password)");
        } catch (Exception e) {
            println e.cause;
            SQL.rollback();
        } finally {
            SQL.close();
        }
    }

    static void queryTest() {
        SQL.eachRow('select * from user') {
           row ->
           println "id : $row.id, username: $row.username, password: $row.password";
        };
    }

    static void updateTest() {
        SQL.connection.autoCommit = false;
        try {
            SQL.execute("update user set password = '111111'");
            SQL.commit();
        }catch (Exception e) {
            SQL.rollback();
        } finally {
            SQL.close();
        }
    }

    static void testDelete() {
        SQL.connection.autoCommit = false;
        try {
            SQL.execute("delete from user");
            SQL.commit();
        }catch (Exception e) {
            SQL.rollback();
        } finally {
            SQL.close();
        }
    }

    static void main(String[] args) {
//        createTableTest();
//        insertValTest();
//        queryTest();
//        updateTest();
        testDelete();
    }
}
