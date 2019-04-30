package com.example.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo01 {

    /**
     * 使用 LoggerFactory 的静态方法 getLogger 创建一个指定名称的 logger, 并且打印一个 debug 级别的日志, 日志内容为: hello world!
     * 从下面的例子中我们注意到: 我们并没有引用任何的 logback 相关的 class, 而是直接使用了 slf4j 的 API, 在以后的使用中, 我们绝大多数
     * 情况下都是使用的 slf4j 相关的 API, 至于其内部实现使用的的 logback 这一点我们可以不用关注.
     */
    @Test
    public void test01() {
        Logger logger = LoggerFactory.getLogger(Demo01.class.getName());
        logger.debug("Hello world.");
    }

    /**
     * logback 内部状态通过一个内部名字叫 StatusManager 的组件的管理, 下面的例子将指导我们如何使用 StatusPrinter 的 print 方法打印出
     * logback 的内部状态信息, 从打印的结果我们可以指导, logback 依次查账 logback-test.xml 和 logback.groovy, logback.xml, 但是都
     * 不能查找到, 所以 logback 默认使用自己内部实现的一个 Appender 打印日志, 既 ConsoleAppender. Appender 是一个可以将日志输出到不同
     * 位置的实现方法, Appender 有许多的实现方式, 如 console、files、Syslog、TCP Socket、JMS 等等. 当然，使用者也可以依据适合自己的使用
     * 场景自己实现一个 Appender.
     *
     * test01、test02 中的例子很简单, 实际上无论多大的应用程序在日志打印上没有什么区别, 打印的格式都差不多, 只是配置的方式不同而已。然而,
     * 许多时候我们需要按照自己的需求配置 logback 以满足我们的业务需求, 别着急, 这些内容将会在后面的章节中详细的介绍.
     *
     * 下面的例子打印出了 logback 在运行过程中的内部状态信息, 这对诊断 logback 运行过程中出现的问题很有帮助,
     *
     * 下面给出了使用 logback 的步骤:
     * 1、配置环境.
     * 2、使用 LoggerFactory 的 getLogger 方法创建一个 logger 实例.
     * 3、使用创建的 logger 实例的 debug、info、warn、error 方法打印日志.
     */
    @Test
    public void test02() {
        Logger logger = LoggerFactory.getLogger(Demo01.class.getName());
        logger.debug("Hello world.");
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
    }

}
