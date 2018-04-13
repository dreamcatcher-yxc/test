package com.example.schedule;

import org.quartz.*;

public class HelloJob implements Job, InterruptableJob
{
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        System.out.println("Hello Quartz!");
        Thread.interrupted();
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        System.out.println("Hello Quartz Interrupted...");
    }
}