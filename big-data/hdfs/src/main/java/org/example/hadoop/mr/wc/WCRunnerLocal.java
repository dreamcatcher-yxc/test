package org.example.hadoop.mr.wc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 用来描述一个特定的作业
 * 比如，该作业使用哪个类作为逻辑处理中的map，哪个作为reduce
 * 还可以指定该作业要处理的数据所在的路径
 * 还可以指定改作业输出的结果放到哪个路径
 * ....
 * @author duanhaitao@itcast.cn
 *
 */
public class WCRunnerLocal {

    private static final Log log = LogFactory.getLog(WCRunnerLocal.class);

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("mapreduce.framework.name", "local");
        conf.set("fs.defaultFS","file:///");

        Job wcjob = Job.getInstance(conf);

        //设置整个job所用的那些类在哪个jar包
        wcjob.setJarByClass(WCRunnerLocal.class);

        //本job使用的mapper和reducer的类
        wcjob.setMapperClass(WCMapper.class);
        wcjob.setReducerClass(WCReducer.class);

        //指定reduce的输出数据kv类型
        wcjob.setOutputKeyClass(Text.class);
        wcjob.setOutputValueClass(LongWritable.class);

        //指定mapper的输出数据kv类型
        wcjob.setMapOutputKeyClass(Text.class);
        wcjob.setMapOutputValueClass(LongWritable.class);

        //指定要处理的输入数据存放路径
        FileInputFormat.setInputPaths(wcjob, new Path("D:/hadoop-tmp/word.txt"));

        //指定处理结果的输出数据存放路径
        FileOutputFormat.setOutputPath(wcjob, new Path("/hadoop-tmp/map-reduce-work"));

        //将job提交给集群运行
        wcjob.waitForCompletion(true);
    }
}
