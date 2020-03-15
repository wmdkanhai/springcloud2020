Ribbon

### 1、概述

#### 1.1、是什么

Spring Cloud Ribbon 是基于 Netflix Ribbon实现的一套客户端 负载均衡工具



Ribbon 是 Netflix 发布的开源项目，主要功能是提供客户端的软件负载均衡算法和服务调用。Ribbon 客户点组件提供一系列完善的配置项，比如超时，重试等。



简单的说，就是在配置文件中列出 Load Balancer(LB)后面所有的机器，Ribbon会自动的帮助你基于某种规则（如简单轮询，随机连接等）去连接这些机器，我们很容易使用 Ribbon 实现自定义的负载均衡算法。



#### 1.2、能干啥

- LB（负载均衡）
  - 集中式LB（在服务的消费方和提供方之间使用独立的LB设施，如 F5、也可以使用软件 Nginx），由该设施负责把请求通过某种策略转志服务的提供方
  - 进程内LB（将LB的逻辑集成到消费方，消费方从服务注册中心获知有哪些地址可用，然后自己再从这些地址中选择出一个合适的服务器。Ribbon就属于进程内LB，它只是一个类库，集成于消费方进程，消费方通过它来获取到服务提供方的地址）

负载均衡 + RestTemplate调用

### 2、Ribbon负载均衡

#### 2.1、架构说明

- 先选择 EurekaServer ，它优先选择在同一个区域负载较少的 server
- 根据用户指定的策略，再从 server 取到的服务注册列表中选择一个地址，让消费者访问这个地址。其中 Ribbon 提供了多种策略：轮询、随机、根据响应时间加权

#### 2.2、POM

我们在使用过程中，并没有添加Ribbon的依赖，但是可以使用，这个是因为在我们引入  spring-cloud-starter-netflix-eureka-server 中已经依赖了 Ribbon

#### 2.3、RestTemplate的使用总结

- getForObject、getForEntity
- postForObject、postEntity
- GET 请求方法
- POST 请求方法



### 3、Ribbon 核心组件 IRule

#### 3.1、IRule

根据特定算法从服务列表中选取一个要访问的服务

- com.netflix.loadbalancer.RoundRobinRule 轮询
- com.netflix.loadbalancer.RandomRule 随机
- com.netflix.loadbalancer.RetryRule 先按照RoundRobinRule的策略获取服务,如果获取服务失败则在指定时间内进行重试,获取可用的服务
- WeightedResponseTimeRule 对RoundRobinRule的扩展,响应速度越快的实例选择权重越多大,越容易被选择
- BestAvailableRule 会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务,然后选择一个并发量最小的服务
- AvailabilityFilteringRule 先过滤掉故障实例,再选择并发较小的实例
- ZoneAvoidanceRule 默认规则,复合判断server所在区域的性能和server的可用性选择服务器



