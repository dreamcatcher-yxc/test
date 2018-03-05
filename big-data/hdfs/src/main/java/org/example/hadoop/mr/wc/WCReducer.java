package org.example.hadoop.mr.wc;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Mapper 泛型参数说明:
 * 1. 表示输入的键类型
 * 2. 表示输入的值类型
 * 3. 表示输出的键类型
 * 4. 表示输出的值类型
 */
public class WCReducer extends Reducer<Text, LongWritable, Text, LongWritable>{

    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        long count = 0;
        for(LongWritable val : values) {
            count += val.get();
        }
        context.write(key, new LongWritable(count));
    }
}
