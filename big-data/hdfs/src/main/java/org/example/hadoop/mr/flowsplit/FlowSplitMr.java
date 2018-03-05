package org.example.hadoop.mr.flowsplit;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.example.hadoop.mr.flowsum.FSBean;
import org.example.hadoop.mr.flowsum.FSRunner;

import java.io.IOException;

/**
 * 对流量原始日志进行流量统计，将不同省份的用户统计结果输出到不同文件
 * 需要自定义改造两个机制：
 * 1、改造分区的逻辑，自定义一个partitioner
 * 2、自定义reduer task的并发任务数
 */
public class FlowSplitMr {

    private static final Log log = LogFactory.getLog(FSRunner.class);

    public static class FlowSplitMapper extends Mapper<LongWritable, Text, Text, FSBean> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String row = value.toString();
            String[] eles = row.split("\\s+");
            if(eles.length >= 3) {
                String number = eles[0];
                Long upFlows = Long.parseLong(eles[1]);
                Long downFlows = Long.parseLong(eles[2]);
                context.write(new Text(number), new FSBean(upFlows, downFlows));
            }
        }
    }

    public static class FlowSplitReducer extends Reducer< Text, FSBean,  Text, FSBean>{
        @Override
        protected void reduce(Text key, Iterable<FSBean> values, Context context) throws IOException, InterruptedException {
            long upFlowSum = 0, downFlowSum = 0;
            for (FSBean value : values) {
                upFlowSum += value.getUpFlows();
                downFlowSum += value.getDownFlows();
            }
            context.write(key, new FSBean(upFlowSum, downFlowSum));
        }
    }
    public static void main(String[] args) throws Exception {
        if(ArrayUtils.isEmpty(args) || args.length < 2) {
            log.error("argument least 2!!!");
            return;
        }

        String inputDir = args[0];
        String outputDir = args[1];

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(SplitPartitioner.class);

        job.setMapperClass(FlowSplitMapper.class);
        job.setReducerClass(FlowSplitReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FSBean.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FSBean.class);

        //设置我们自定义的分组逻辑定义
        job.setPartitionerClass(SplitPartitioner.class);
        //设置reduce的任务并发数，应该跟分组的数量保持一致
        job.setNumReduceTasks(4);

        FileInputFormat.setInputPaths(job, new Path(inputDir));

        FileOutputFormat.setOutputPath(job, new Path(outputDir));
        job.waitForCompletion(true);
    }
}
