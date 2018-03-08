# 1. 说明
- basic: 工作队列
- fanout: fanout 类型的 exchange
    - 实现简单的日志系统
    - 会将消息发送给所有消费者
- direct: direct 类型的 exchange
    - 会将消息队列里面的消息按照 routingKey 相应的消费者
- topic: topic 类型 exchange
    - 和 direct 类似, 但是 routing 必须满足如下条件:
        - 多个单词组成, 用 `.` 分割
    - routingKey 支持匹配规则, `#` 代替0个或者多个单词, `*` 代替一个单词
- rpc: 使用 rabbbitMQ 实现远程过程调用
 