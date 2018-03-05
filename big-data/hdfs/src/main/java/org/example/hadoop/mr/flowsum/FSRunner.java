package org.example.hadoop.mr.flowsum;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FSRunner {
    private static final Log log = LogFactory.getLog(FSRunner.class);

    public static void main(String[] args) throws Exception {
        if(ArrayUtils.isEmpty(args) || args.length < 2) {
            log.error("argument least 2!!!");
            return;
        }

        String inputDir = args[0];
        String outputDir = args[1];

        Configuration conf = new Configuration();

        Job wcjob = Job.getInstance(conf);

        wcjob.setJarByClass(FSRunner.class);

        wcjob.setMapperClass(FSMapper.class);
        wcjob.setReducerClass(FSReducer.class);

        wcjob.setOutputKeyClass(Text.class);
        wcjob.setOutputValueClass(FSBean.class);

        wcjob.setMapOutputKeyClass(Text.class);
        wcjob.setMapOutputValueClass(FSBean.class);

        FileInputFormat.setInputPaths(wcjob, new Path(inputDir));

        FileOutputFormat.setOutputPath(wcjob, new Path(outputDir));

        wcjob.waitForCompletion(true);
    }
}
