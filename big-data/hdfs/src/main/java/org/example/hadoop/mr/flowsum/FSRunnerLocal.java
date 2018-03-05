package org.example.hadoop.mr.flowsum;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FSRunnerLocal {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("mapreduce.framework.name", "local");
        conf.set("fs.defaultFS","file:///");

        Job wcjob = Job.getInstance(conf);

        wcjob.setJarByClass(FSRunnerLocal.class);

        wcjob.setMapperClass(FSMapper.class);
        wcjob.setReducerClass(FSReducer.class);

        wcjob.setOutputKeyClass(Text.class);
        wcjob.setOutputValueClass(FSBean.class);

        wcjob.setMapOutputKeyClass(Text.class);
        wcjob.setMapOutputValueClass(FSBean.class);

        FileInputFormat.setInputPaths(wcjob, new Path("D:/hadoop-tmp/numbers-01.log"));

        FileOutputFormat.setOutputPath(wcjob, new Path("D:/hadoop-tmp/map-reduce-work"));

        wcjob.waitForCompletion(true);
    }
}
