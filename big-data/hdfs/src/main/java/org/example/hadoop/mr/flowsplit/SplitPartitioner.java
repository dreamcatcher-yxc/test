package org.example.hadoop.mr.flowsplit;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;
import org.example.hadoop.mr.flowsum.FSBean;

public class SplitPartitioner extends Partitioner<Text, FSBean> {

    public int getPartition(Text text, FSBean fsBean, int numPartitions) {
        String phoneNums = text.toString();
        if(phoneNums.length() < 3) {
            return -1;
        }
//        "13241980712",  "13441880712","13641908712","18441981270","18411089712","18512980712","18643123241"
        String prefix = phoneNums.substring(0, 3);
        if("132".equalsIgnoreCase(prefix) || "134".equalsIgnoreCase(prefix) || "136".equalsIgnoreCase(prefix)) {
            return 0;
        } else {
            return 1;
        }
    }
}
