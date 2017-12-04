package org.example.groovy

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*

/**
 * @DATE 2017/12/1 8:59
 * @author yangxiuchu
 */
class Demo03 {

    def static void closureTest() {
        def sayHello = { name -> println "hello $name!" };
        sayHello.call('杨秀初');
        println '*' * 30;
        def list = [1, 2, 3, 4];
        list.each { println it };
        println '*' * 30;
        def map = ['username': 'zhangsan', 'password': '123456', 'age': 20];
        map.each { println "$it.key -> $it.value" };

        println '*' * 30;
        // find: 查找符合条件的第一个元素
        // findAll: 查找符合条件的所有元素
        // any & every: 类似于数据库的 any & every, 返回 boolean 类型的值
        // collect: 相当于 $.map
        def arr = [1, 2, 3, 4];
        println arr.find { it > 2 }; // 3(因为 3 第一个满足条件).
        println arr.findAll { it > 2 }; // [3, 4]
        println arr.any { it > 2 }; // true(部分满足条件)
        println arr.every { it > 2 }; // false(不是所有的元素都满足该条件)
        println arr.collect { it**2 }; // [1, 4, 9, 16]
    }

    // 了解, 暂时不用
    def static void annotationTest() {

    }

    // groovy 对 xml 的支持, jmx 暂时不用
    def static void xmlTest() {

    }

    def static void httpTest() {
        HTTPBuilder http = new HTTPBuilder('http://localhost:9096');
        http.request( GET, TEXT ) {
            println it;
            url.path = '/test/editableTable/'
//            url.query = [] // get 方式使用此方式配置参数。
//            body = [] // post 方式使用此方式配置参数。
            headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'
            response.success = { resp, reader ->
                println reader.text;
            }
            response.failure = { resp -> println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}" }
        }
    }

    def static void jsonTest() {
        def jsonSlurper = new JsonSlurper();
        Object lst = jsonSlurper.parseText('{ "List": [2, 3, 4, 5] }');
        // 解析基本数据类型列表
        def obj = jsonSlurper.parseText(''' {"integer": 12, "fraction": 12.55, "double": 12e13}''');
//        lst.each { println it };
        println obj.integer;
        println obj.fraction;
        println obj.double;
        // JsonOutput: 将数据装换为 json 字符串.
        println JsonOutput.toJson([username : 'zhangsan', age : 20, gender : 'male', email : 'zhangsan@sina.com']);
    }

    static void main(String[] args) {
//        closureTest()
//        xmlTest();
//        httpTest();
//        httpTest();
        jsonTest();
    }
}
