package org.example.hadoop.mr.flowsum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FSReducer extends Reducer<Text, FSBean, Text, FSBean> {

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
