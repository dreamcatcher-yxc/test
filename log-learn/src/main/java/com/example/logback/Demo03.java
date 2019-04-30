package com.example.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo03 {

    /**
     * 在初始化阶段, logback 会依次寻找 classpath: logback-test.xml => classpath: logback.groovy => classpath: logback.xml, 如果找不到,
     * 则会寻找 META-INF\services\ch.qos.logback.classic.spi.Configurator 的实现类, 如果依然寻找不到, 则 logback 将使用自己实现的 Configurator
     * 作为默认配置.
     */
    @Test
    public void test01() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        // print logback's internal status
        StatusPrinter.print(lc);
    }

    /**
     * 可以通过设置 java 应用启动参数或者设置系统变量的方式配置自定义配置文件位置.
     */
    @Test
    public void test02() {
        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, "config.xml");
        Logger rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.info("hello world!");
    }

    /**
     * logback-core 使用 JoranConfigurator 作为配置工具, 我们可以通过如下的方式直接操作 JoranConfigurator,
     * 从而跳过 logback 默认的配置机制.
     */
    @Test
    public void test04() {
        // assume SLF4J is bound to logback in the current environment
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        try {
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            // Call context.reset() to clear any previous configuration, e.g. default
            // configuration. For multi-step configuration, omit calling context.reset().
            context.reset();
            configurator.doConfigure("D:\\data\\IdeaProjects\\java\\log-learn\\src\\main\\resources\\config.xml");
        } catch (JoranException je) {
            // StatusPrinter will handle this
            je.printStackTrace();
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
    }

    /**
     * 可以通过如下的方式停止 logback, 同时也解除了 logback-classic 对资源的占用, 当然, 也可通过在配置文件的根元素下添加:
     *  <shutdownHook/> 替带如下配置.
     */
    @Test
    public void test05() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.stop();
    }

    /**
     * <p>
     * 使用配置文件的方式指定 logback 的行为, 可以在不用重新编译我们代码的情况下改变 logback 的行为, 实际上，
     * 您可以轻松地配置logback，以便禁用应用程序某些部分的日志记录，或者将输出直接输出到UNIX系统日志守护程序、
     * 数据库、日志可视化工具，或者将日志记录事件转发到远程logback服务器，该服务器将根据本地服务器策略进行日志
     * 记录，例如将日志事件转发到第二个logback服务器。
     * </p>
     *
     * <p>
     * 配置文件允许有一个名字叫 configuration 的根元素, configuration 元素下允许有出现三种类型的元素:
     *  appender: 允许出现 0 ~ n 个.
     *  logger: 允许出现 0 ~ n 个.
     *  root: 必须有, 仅仅只能有一个.
     *  如图: pic2.png
     * </p>
     *
     *  <h2>区分大小写的标签名称:</h2>
     *
     * <p>
     * 由于logback版本0.9.17，与显式规则相关的标记名不区分大小写。例如，<logger>、<Logger>和<LOGGER>是有效的配置元素，
     * 将以相同的方式进行解释。请注意，XML格式良好的规则仍然适用，如果您以<xyz>的形式打开标记，则必须将其关闭为<xyz>，
     * <XyZ>将不起作用。对于隐式规则，标记名区分大小写，除了第一个字母。因此，<xyz>和<Xyz>是等效的，但不是。隐式规则
     * 通常遵循 CAMELCASE 约定，在Java世界中是常见的。由于不容易判断标记何时与显式操作关联，何时与隐式操作关联，因此不
     * 容易判断XML标记对第一个字母是区分大小写还是不区分大小写。如果您不确定对给定的标记名使用哪种情况，只需遵循camelcase
     * 约定，它几乎总是正确的约定。
     * </p>
     *
     * <h2> logger 配置</h2>
     * <p>
     *    您最好在掌握了 logback 的 "日志级别继承规则" 和 "默认日志级别配置" 的前提下阅读下文, 否则将有碍于您对如下介绍内容
     *    的掌握.
     * </p>
     * <p>
     *     &lt;logger&gt; 标签允许出现如下三个属性:
     *     <ul>
     *         <li>name: logger 名称(必须).</li>
     *         <li>level: 日志打印级别, 大小不敏感, 允许出现: TRACE, DEBUG, INFO, WARN, ERROR, ALL, OFF, INHERITED(继承), NULL(继承父 logger 的级别).</li>
     *         <li>additivity: true | false.</li>
     *     </ul>
     *
     *     &lt;logger&gt; 下可以添加 0 ~ n 个 appender-ref 元素, 注意: 与 log4j 不同, logback classic在配置给定记录器时不会关闭或删除任何以前引用的 appender.
     * </p>
     *
     * <h2> root 配置</h2>
     * <p>
     *     &lt;logger&gt; 只允许有一个 level 属性, 可以为: TRACE, DEBUG, INFO, WARN, ERROR, ALL, OFF, INHERITED(继承), NULL(继承父 logger 的级别).
     *     和 &lt;logger&gt; 类似,  &lt;root&gt; 下可以添加 0 ~ n 个 appender-ref 元素, 注意: 与 log4j 不同, logback classic在配置给定记录器时不会关闭或删除任何以前引用的 appender.
     * </p>
     */
    @Test
    public void test06() {
        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, "config-03-06.xml");
        Logger logger1 = LoggerFactory.getLogger("chapters.configuration");
        Logger logger2 = LoggerFactory.getLogger("foo");
        logger1.debug("logger1");
        logger2.debug("logger2");
    }

    @Test
    public void test07() {
        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, "config-03-06-02.xml");
        Logger logger1 = LoggerFactory.getLogger("foo");
        Logger logger2 = LoggerFactory.getLogger("chapters.configuration");
        Logger logger3 = LoggerFactory.getLogger("chapters.configuration.foo");
        logger1.info("logger1");
        logger2.info("logger2");
        logger3.info("logger3");
    }
}
