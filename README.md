# info-mq

消息确认 
https://www.cnblogs.com/milicool/p/9662447.html  
https://blog.csdn.net/linpeng_1/article/details/80505828  
https://huan1993.iteye.com/blog/2433263  
https://blog.csdn.net/otherCoco/article/details/82893654
https://blog.csdn.net/qq_29663071/article/details/81559032
https://blog.csdn.net/q547550831/article/details/78506906  
https://www.cnblogs.com/pypua/articles/7519213.html
https://blog.51cto.com/4925054/2096781  


延时支付队列   
https://segmentfault.com/a/1190000016072908  
https://blog.csdn.net/zhangyuxuan2/article/details/82986802  
https://blog.csdn.net/linsongbin1/article/details/80178122   延迟3秒 



那么就单单实现在提交订单后的15分钟内，如果没有完成支付，系统关闭订单。有哪些可行的方案呢。

方案:
1.使用定时任务轮询订单表，查询出订单创建了15分钟以上并且未支付的订单，如果有查询出此类订单则执行关闭。
	缺点：假设每1分钟轮询一次，则会存在秒级误差，如果秒级轮询，则会极其消耗性能，影响程序的健壮性。
	
2.提交订单时开启一个新线程，而新线程直接休眠15分钟，休眠结束后开始执行订单关闭
	缺点：如果在线程休眠时，重启了整个服务，那么会怎样呢？

3. 使用延时消息队列
	缺点：需要额外部署消息中间件
	
综上考虑：使用延时消息队列则成为最佳选择，消息延时发布之后，保存在消息中间件中，
在15分钟后才会正式发布至队列，延时队列监听器在15分钟后监听到消息时，才开始执行，而这期间，即使项目重启也没有关系。


事务 
https://my.oschina.net/lzhaoqiang/blog/670749


延时功能 插件下载地址 x-delayed-message
https://www.rabbitmq.com/community-plugins.html 放到rabbitmq_server-3.7.12\plugins
https://www.jianshu.com/p/2ecad37d64ff
rabbitmq-plugins enable rabbitmq_delayed_message_exchange   sbin目录下启用插件 

rabbitmq-service start 开始服务
Rabbitmq-service stop  停止服务
Rabbitmq-service enable 使服务有效
Rabbitmq-service disable 使服务无效
rabbitmq-service help 帮助


rabbitmq-plugins enable rabbitmq_management 启动
rabbitmq-plugins disable rabbitmq_management 关闭


direct = routing key 一致
topic = * ,# 模糊匹配
header = whereAll 全部匹配  whereAny 一个匹配
fanout = 广播模式 和 routingkey 没关系 


利用插件 x-delayed-message 实现延时消费

2019-03-06 
类上面新增注解加RabbitHandler ,  去掉配置文件  autoDelete = "false" 持久化防止消息丢失 
 @RabbitListener(bindings = @QueueBinding(
         value=@Queue(value = "${mq.config.queue}",autoDelete = "false"),
         exchange = @Exchange(value = "${mq.config.exchange}",type = ExchangeTypes.DIRECT),
         key = "${mq.config.info.key}"))
@RabbitHandler 

新增 重试机制 ，如果消费者抛异常，最多重试 5次 
#是否支持重试
spring.rabbitmq.listener.simple.retry.enabled=true
#最大重试次数
spring.rabbitmq.listener.simple.retry.max-attempts=5

ack 手动确认机制
什么是消息确认ACK?
如果在处理消息过程中，消费者的服务器在处理消息时出现异常，那可能这条这
条在处理的消息就没有完成消息消费，数据就会丢失，为了确保数据不会丢失，
RabbitMQ支持消息确认ack。

ACK的消息确认机制。
ACK机制是消费者从rabbitmq收到消息并处理完成后，反馈给RabbitMq，rabbitmq收到反馈后
才将消息从队列中删除。

1.如果一个消费者在处理消息出现了网络不稳定、服务器异常等现象，那么就不会有ACK反馈，
RabbitMQ会认为这个消息没有正常消费，会将消息重新放入队列中.

2.如果在集群的情况下: Rabbi tQ会立即将这个消息推送给这个在线的其他消费者。
这种机制保证了在消费者服务端故障的时候，不丢失任何消息和任务.

3.消息永远不会从RabbltM中删除:只有当消费者正确发送ACK反馈，RabbitMQ确认收到后，
消息才会从RabbitMQ服务器的数据中删除

4.消息的ACK确认机制默认是打开的。

3. ACK机制的开发注意事项
如果忘记了ACK,那么后果很严重。当Consuer退出时，
Message会一直重新分发然后RabbltMQ会占用越来越多的内存，
由于RabbitMQ会长时间运行，因此这个“内存泄漏”是致命的。



//向服务端发送ack，确认我收到消息了  【确认消息】
//channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//ack返回false，并重新回到队列，api里面解释得很清楚
//channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
//第一个参数指定 delivery tag， 【取消确认】
//第二个参数说明如何处理这个失败消息。requeue 值为 true 表示该消息重新放回队列头，
//值为 false 表示放弃这条消息,相当于告诉队列可以直接删除掉
channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);

channel.basicNack 与 channel.basicReject 的区别在于basicNack可以拒绝多条消息，而basicReject一次只能拒绝一条消息


window 搭建 rabbitMQ 集群
https://blog.csdn.net/inaxen/article/details/76607107   
https://blog.csdn.net/niketwo/article/details/79078680
https://blog.csdn.net/u010887744/article/details/78377634  
