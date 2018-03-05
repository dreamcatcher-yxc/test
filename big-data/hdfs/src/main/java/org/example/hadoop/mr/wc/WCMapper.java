package org.example.hadoop.mr.wc;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Mapper 泛型参数说明:
 * 1. 表示输入的键类型
 * 2. 表示输入的值类型
 * 3. 表示输出的键类型
 * 4. 表示输出的值类型
 */
public class WCMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

    /**
     * @param key: 行号
     * @param value: 该行的文本数据
     * @param context: 上下文对象
     * @throws IOException
     * @throws InterruptedException
     */
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] words = StringUtils.split(value.toString(), "\\s+");
        for(String word : words) {
            // 输出映射结果
            context.write(new Text(word), new LongWritable(1));
        }
    }
}
