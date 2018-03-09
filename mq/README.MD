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

# 2. 队列基本使用步骤
## 2.1 发送端(非 exchange)
1. 创建工厂类(factory)
```
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("xxx.xxx.xxx.xxx");
    factory.setPort(5672);
    factory.setUsername("xxx");
    factory.setPassword("xxx");
```
2. 工厂类创建客户端与服务器之间的连接(connection)
```
Connection connection = factory.newConnection();
```
3. 使用 connection 打开信道(channel)
```
Channel channel = connection.createChannel();
```
4. 使用信道定义队列(queue)
```
channel.queueDeclare(QUEUE_NAME, true, false, false, null);
```
5. 发送数据, 使用 channel API
```
String message = "hello world!";
channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
```
6. 关闭信道和连接
```
channel.close();
connection.close();
```

## 2.2 接收端(非 exchange)
1. 创建工厂类(factory)
```
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("xxx.xxx.xxx.xxx");
    factory.setPort(5672);
    factory.setUsername("xxx");
    factory.setPassword("xxx");
```
2. 工厂类创建客户端与服务器之间的连接(connection)
```
Connection connection = factory.newConnection();
```
3. 使用 connection 打开信道(channel)
```
Channel channel = connection.createChannel();
```
4. 使用信道定义队列(queue)
```
channel.queueDeclare(QUEUE_NAME, true, false, false, null);
```
5. 创建队列消费者(QueueingConsumer)
```
QueueingConsumer consumer = new QueueingConsumer(channel);
```
6. 队列消费者配置
```
// autoAck 为 false, 表示需要消息确认。
channel.basicConsume(QUEUE_NAME, false, consumer);
```
7. 队列消费者等待消费队列里面的消息
```
QueueingConsumer.Delivery delivery = consumer.nextDelivery();
String message = new String(delivery.getBody());
System.out.println(message);
```
8. 返回消费确认消息(非必须)
```
// 必须确认消息处理完成, multiple 标示是否已经收到的所有消息，false 标示只是收到了刚才接收到这条消息。
channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
```
9. 关闭信道和连接
```
channel.close();
connection.close();
```
> 非交换机模式直接是直接将信息发送给队列, 消费者直接从相应的队列里面提取消息。
 
 
## 2.3 发送端(exchange)
1. 创建工厂类(factory)
2. 工厂类创建客户端与服务器之间的连接(connection)
3. 使用 connection 打开信道(channel) 
4. 使用 channel 创建 交换机(exchange)
5. 发送数据的时候指定交换机发送即可, 根据不同的交换机类型可能有不同的区别
6. 关闭信道和连接

## 2.3 接收端(exchange)
1. 创建工厂类(factory)
2. 工厂类创建客户端与服务器之间的连接(connection)
3. 使用 connection 打开信道(channel)
4. 使用信道定义队列(queue)
5. 将 queue 和 exchange 绑定
6. 创建队列消费者(QueueingConsumer)
8. 队列消费者配置
9. 队列消费者等待消费队列里面的消息
10. 返回消费确认消息(非必须)
11. 关闭信道和连接

> 接收端根据 exchange 类型的不同可能有所区别, 如果 exchange 类型为
direct、topic 类型, 则需要指定 bindingKey(也可以叫做 routingKey)