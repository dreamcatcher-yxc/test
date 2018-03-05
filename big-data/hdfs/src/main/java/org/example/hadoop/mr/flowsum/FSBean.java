package org.example.hadoop.mr.flowsum;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @note: 序列化和反序列化的顺序必须相同。
 */
public class FSBean implements Writable{
    private Long upFlows;
    private Long downFlows;
    private Long totalFlows;

    public FSBean() {}

    public FSBean(Long upFlows, Long downFlows) {
        this.upFlows = upFlows;
        this.downFlows = downFlows;
        this.totalFlows = this.upFlows + this.downFlows;
    }

    public Long getUpFlows() {
        return upFlows;
    }

    public void setUpFlows(Long upFlows) {
        this.upFlows = upFlows;
    }

    public Long getDownFlows() {
        return downFlows;
    }

    public void setDownFlows(Long downFlows) {
        this.downFlows = downFlows;
    }

    public Long getTotalFlows() {
        return totalFlows;
    }

    /**
     * 序列化
     * @param out
     * @throws IOException
     */
    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlows);
        out.writeLong(downFlows);
    }

    /**
     * 反序列化
     * @param in
     * @throws IOException
     */
    public void readFields(DataInput in) throws IOException {
        upFlows = in.readLong();
        downFlows = in.readLong();
        totalFlows = upFlows + downFlows;
    }

    @Override
    public String toString() {
        return String.format("%d\t%d\t%d",upFlows, downFlows, totalFlows);
    }

    //    public static void main(String[] args) throws Exception{
//        String[] numbers = {"13241980712",  "13441880712","13641908712","18441981270","18411089712","18512980712","18643123241"};
//        StringBuffer sb = new StringBuffer();
//
//        for(int i = 0; i < 100; i++) {
//            String number = numbers[(int)(Math.random() * numbers.length)];
//            String upFlows = String.valueOf((int)(Math.random() * 900000 + 100000));
//            String downFlows = String.valueOf((int)(Math.random() * 900000 + 100000));
//            sb.append(String.format("%s\t%s\t%s\r\n", number, upFlows, downFlows));
//        }
//
//        FileOutputStream os = new FileOutputStream("D:/hadoop-tmp/numbers-01.log");
//        os.write(sb.toString().getBytes());
//        os.close();
//    }
}
