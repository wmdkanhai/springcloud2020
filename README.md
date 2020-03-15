SpringCloud学习记录

### 模块介绍
- cloud-consumer-order80 订单服务，通过注册中心去调用其他服务
- cloud-provider-payment8001 支付服务，提供服务供其他服务调用
- cloud-eureka-server7001 注册中心

- 将 cloud-provider-payment8004 注册到 zookeeper 上，提供服务
- 将 cloud-consumerzk-order80 也注册到 zookeeper 上，去消费服务

- 将 cloud-providerconsul-payment8006 注册到 Consul 上，提供服务
- 将 cloud-consumerconsul-order80 注册到 Consul 上，去消费服务