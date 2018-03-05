package org.example.hadoop.mr.flowsum;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FSMapper extends Mapper<LongWritable, Text, Text, FSBean> {

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
