Hystrix 熔断器

### 1、概述

#### 1.1、分布式系统面临的问题

分布式系统面临的问题

复杂分布式体系结构中的应用程序，有数10个依赖关系,每个依赖关系在某些时候将不可避免地失败

- 服务雪崩

多个微服务之间调用的时候，假设微服务A调用微服务B和微服务C，微服务B和微服务C又调用其他的微服务，这就是所谓的“扇出”。如果扇出的链路上某个微服务的调用响应时间过长或者不可用，对微服务A的调用就会占用越来越多的系统资源，进而引起系统崩溃，所谓的 雪崩效应

#### 1.2、HyStrix是什么



Hystrix是一个用于处理分布式系统的延迟和容错的开源库。在分布式系统中，许多依赖不可避免的会调用失败，比如超时，异常等，Hystrix能够保证在一个依赖出现问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性。

当某个服务出现故障后，通过熔断器的故障监控，向调用方返回一个符合预期的，可处理的备选响应（fallback），而不是长时间的等待或者抛出调用方式无法处理的异常，这样就保证了服务调用方的线程不会被长时间，不必要的占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。



#### 1.3、能干什么

- 服务降级

- 服务熔断

- 接近实时的监控

  

#### 1.4、官方资料

https://github.com/Netflix/hystrix/wiki

Hystrix官宣，停更进维

### 2、重要的一些概念



#### 2.1、服务降级

服务器忙，请稍后再试，不再让客户端等待并立刻返回一个友好的提示，fallback



哪些情况下会发出降级

- 程序运行异常
- 超时
- 服务熔断触发服务降级
- 线程池，信号量也会导致服务降级

#### 2.2、服务熔断

类比保险丝达到最大访问后，直接拒绝访问，拉闸限电，然后调用服务降级的方法并返回友好提示

服务降级-->进而熔断-->恢复调用链路

#### 2.3、服务限流

秒杀高并发等操作,严禁一窝蜂的过来拥挤,大家排队,一秒钟N个,有序进行

### 3、HyStrix案例



还只是服务提供者8001自己测试,假如此时外部的消费者80也来访问,那消费者只能干等,最终导致消费端80不满意,服务端8001直接被拖死



故障和导致现象

8001同一层次的其他接口被困死,因为tomcat线程池里面的工作线程已经被挤占完毕

80此时调用8001,客户端访问响应缓慢,转圈圈



正因为有上述故障或不佳表现 才有我们的降级/容错/限流等技术诞生





#### 如何解决

- 超时导致服务器变慢---》超时不再等待
- 出错（宕机或程序运行出错）---》出错要有兜底，有fallback

总之，出现不正常情况后，进行服务降级



### 4、HyStrix工作流程

### 5、服务监控hystrixDashboard

