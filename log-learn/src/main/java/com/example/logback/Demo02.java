package com.example.logback;

import ch.qos.logback.classic.Level;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo02 {
    @Test
    public void test01() {
        Logger rootLogger = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        rootLogger.info("hello world!");
    }

    /**
     * 日志分为五个级别以及其优先级优先级为: TRACE < DEBUG < INFO < WARN < ERROR.
     * 打印日志的规则为: 低级别的日志总是能被高级别的 logger 打印出来, 例如 logger
     * 日志级别为 debug, 则 debug()、info()、warn()、error() 打印的日志可以打印
     * 出来, trace() 无法打印.
     *
     * 在logback中，日志级别是配置给logger的，负责设定一个logger记录的日志等级。当我们没有为一个logger设置日志级别时，
     * 系统会沿着logger数朝着根节点方向查找，寻找最近一个设置了日志级别的父logger或先人logger。查找到最上面当然就是
     * 上文提到的root logger，而root logger的默认日志等级是debug级, pic01.png 是相应的例子.
     */
    @Test
    public void test02() {
        // get a logger instance named "com.foo". Let us further assume that the
        // logger is of type  ch.qos.logback.classic.Logger so that we can
        // set its level
        ch.qos.logback.classic.Logger logger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.foo");
        //set its Level to INFO. The setLevel() method requires a logback logger
        logger.setLevel(Level.INFO);

        Logger barlogger = LoggerFactory.getLogger("com.foo.Bar");

        // This request is enabled, because WARN >= INFO
        logger.warn("Low fuel level.");

        // This request is disabled, because DEBUG < INFO.
        logger.debug("Starting search for nearest gas station.");

        // The logger instance barlogger, named "com.foo.Bar",
        // will inherit its level from the logger named
        // "com.foo" Thus, the following request is enabled
        // because INFO >= INFO.
        barlogger.info("Located nearest gas station.");

        // This request is disabled, because DEBUG < INFO.
        barlogger.debug("Exiting gas station search");
    }

    /**
     * 同名 logger 实例相同
     */
    @Test
    public void test03() {
        Logger x = LoggerFactory.getLogger("wombat");
        Logger y = LoggerFactory.getLogger("wombat");
        System.out.println(x == y); // true
    }

    /**
     * 日志打印参数
     */
    @Test
    public void test04() {
        Logger logger = LoggerFactory.getLogger("foo");
        logger.info("name: {}, age: {}", "xiuchu.yang", "24");
    }

}
