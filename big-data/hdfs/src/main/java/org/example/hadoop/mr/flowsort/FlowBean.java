package org.example.hadoop.mr.flowsort;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @note: 序列化和反序列化的顺序必须相同。
 */
public class FlowBean implements WritableComparable<FlowBean>{
    private String number;
    private Long upFlows;
    private Long downFlows;
    private Long totalFlows;

    public FlowBean() {}

    public FlowBean(String number, Long upFlows, Long downFlows) {
        this.number = number;
        this.upFlows = upFlows;
        this.downFlows = downFlows;
        this.totalFlows = this.upFlows + this.downFlows;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
        out.writeUTF(number);
        out.writeLong(upFlows);
        out.writeLong(downFlows);
    }

    /**
     * 反序列化
     * @param in
     * @throws IOException
     */
    public void readFields(DataInput in) throws IOException {
        number = in.readUTF();
        upFlows = in.readLong();
        downFlows = in.readLong();
        totalFlows = upFlows + downFlows;
    }

    public int compareTo(FlowBean o) {
        return totalFlows < o.getTotalFlows() ? 1 : -1;
    }

    @Override
    public String toString() {
        return String.format("number: %s\tup_flows: %d\tdown_flows: %d\ttotal_flows: %d",number, upFlows, downFlows, totalFlows);
    }
}
