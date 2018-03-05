package org.example.hadoop.mr.flowsort;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FlowSortMr {

    private static final Log log = LogFactory.getLog(FlowSortMr.class);

    public static class FlowSortMapper extends Mapper<LongWritable, Text, FlowBean, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String row = value.toString();
            String[] eles = row.split("\\s+");
            if(eles.length >= 3) {
                String number = eles[0];
                Long upFlows = Long.parseLong(eles[1]);
                Long downFlows = Long.parseLong(eles[2]);
                context.write(new FlowBean(number, upFlows, downFlows), NullWritable.get());
            }
        }
    }

    public static class FlowSortReducer extends Reducer<FlowBean, NullWritable, FlowBean, NullWritable>{

        @Override
        protected void reduce(FlowBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }

    public static void main(String[] args) throws Exception{
        if(ArrayUtils.isEmpty(args) || args.length < 2) {
            log.error("argument least 2!!!");
            return;
        }

        String inputDir = args[0];
        String outputDir = args[1];

        Configuration conf = new Configuration();
        Job wcjob = Job.getInstance(conf);
        wcjob.setJarByClass(FlowSortMr.class);

        wcjob.setMapperClass(FlowSortMr.FlowSortMapper.class);
        wcjob.setReducerClass(FlowSortMr.FlowSortReducer.class);

        wcjob.setOutputKeyClass(FlowBean.class);
        wcjob.setOutputValueClass(NullWritable.class);

        wcjob.setMapOutputKeyClass(FlowBean.class);
        wcjob.setMapOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(wcjob, new Path(inputDir));
        FileOutputFormat.setOutputPath(wcjob, new Path(outputDir));

        wcjob.waitForCompletion(true);
    }

}
