package com.example.schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

public class Demo01 {

    public static void main(String[] args) throws Exception {
        // 任务标记
        JobKey jobKey = new JobKey("dummyJobName", "group1");

        Trigger trigger = TriggerBuilder
                // 新建一个触发器
                .newTrigger()
                // 设置标记
                .withIdentity("dummyJobName", "group1")
                // 配置触发器的计划表
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(5).repeatForever())
                // 创建触发器
                .build();

        // 创建一个新的任务说明
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity(jobKey).build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        // 添加作业执行的监听器, 需要指定监听的作业.
        scheduler.getListenerManager()
                .addJobListener(new HelloJobListener(),  KeyMatcher.keyEquals(jobKey));

        scheduler.start();
        scheduler.scheduleJob(job, trigger);

        Thread.sleep(10 * 1000);
        scheduler.deleteJob(jobKey);
    }

}
