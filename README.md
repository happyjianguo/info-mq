# info-mq

消息确认 
https://www.cnblogs.com/milicool/p/9662447.html  
https://blog.csdn.net/linpeng_1/article/details/80505828  
https://huan1993.iteye.com/blog/2433263  
https://blog.csdn.net/otherCoco/article/details/82893654
https://blog.csdn.net/qq_29663071/article/details/81559032


延时支付队列   
https://segmentfault.com/a/1190000016072908  
https://blog.csdn.net/zhangyuxuan2/article/details/82986802  
https://blog.csdn.net/linsongbin1/article/details/80178122   延迟3秒 

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

 


