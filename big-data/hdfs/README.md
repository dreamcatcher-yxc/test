#1.目录结构说明
- example.hadoop.hdfs: 测试 hdfs 文件系统的基本 CRUD 操作。
- example.hadoop.rpc: hadoop 提供的 RPC（远程过程调用）测试。
- example.hadoop.mr: MapReduce 实现单词统计功能。
- example.hadoop.flowsum: MapReduce 实现流量统计功能。
- example.hadoop.flowsort: MapReduce 实现按照总流量排序功能。
- eaample.hadoop.flowsplit: MapReduce 实现将流量统计结果按照所属的地区分文件存储。
    - 改造分区的逻辑，自定义一个partitioner
    - 自定义reduer task的并发任务数
> hdfs 文件系统 Java 客户端测试。