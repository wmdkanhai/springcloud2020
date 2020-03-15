Consul 

### 1、简介

#### 1.1、是什么

https://www.consul.io/intro/index.html

Consul 是一套开源的分布式服务发现和配置管理系统，由 HashiCorp 公司使用 Go 语言开发。



提供了微服务系统中的服务治理、配置中心、控制总线等功能，这些个功能中的每一个可以根据需要单独使用，也可以一起使用以构建全方位的服务网络，总之 Consul 体用了一种完整的服务网格解决方案。



它具有很多优点，包括：基于 raft协议，比较简洁；支持健康检查，同时支持 HTTP 和 DNS 协议，支持跨数据中心的 WAN 集群，提供图形界面，跨平台，支持 Linux、Mac、Windows。

#### 1.2、能干什么



- 服务发现，提供 HTTP、DNS两种发现方式

- 健康监测，支持多种方式，HTTP、TCP、Docker、shell 脚本定制化

- KV存储，Key、Value的存储方式

- 多数据中心，支持多数据中心

- 可视化界面

  

#### 1.3、从哪里下载

https://www.consul.io/downloads.html

#### 1.4、怎么使用

https://www.springcloud.cc/spring-cloud-consul.html

### 2、安装并运行

https://learn.hashicorp.com/consul/getting-started/install.html

Mac上安装

> 将解压后的文件consul  拷贝到/usr/local/bin下sudo cp consul /usr/local/bin

启动

> consul agent -dev

访问

通过以下地址可以访问 Consul 的首页: http://localhost:8500



### 3、服务提供者

#### 3.1、新建Module支付服务provider8006

cloud-providerconsul-payment8006

#### 3.2、POM

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>mycloud2020</artifactId>
        <groupId>com.wmding.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-providerconsul-payment8006</artifactId>
    <description>支付服务的提供者之注册中心Consul</description>


    <dependencies>
        <!--SpringCloud consul-server-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
        </dependency>

        <dependency>
            <groupId>com.wmding.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

    </dependencies>

</project>
```

#### 3.3、YML

```
server:
  port: 8006
spring:
  application:
    name: cloud-provider-payment

  cloud:
    #Consul注册中心地址
    consul:
      host: 127.0.0.1
      port: 8500
      discovery:
        service-name: ${spring.application.name}
```



#### 3.4、主启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain8006 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8006.class, args);
    }
}
```



#### 3.5、业务类

```java
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/payment/consul")
    public String paymentConsul(){
        String msg = "SpringCloud with consul " + serverPort + "\t" + UUID.randomUUID().toString();
        log.info(msg);
        return msg;
    }

}
```

#### 3.6、测试

访问服务地址：http://127.0.0.1:8006/payment/consul

### 4、服务消费者

#### 4.1、新建Module消费服务order80

cloud-consumerconsul-order80

#### 4.2、POM

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>mycloud2020</artifactId>
        <groupId>com.wmding.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-consumerconsul-order80</artifactId>
    <description>服务消费者注册中心 Consul</description>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
        </dependency>

        <dependency>
            <groupId>com.wmding.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

    </dependencies>

</project>
```



#### 4.3、YML

```
server:
  port: 80

spring:
  application:
    name: cloud-consumer-order

  cloud:
    consul:
      # Consul 注册中心地址
      host: localhost
      port: 8500
      discovery:
        hostname: 127.0.0.1
        service-name: ${spring.application.name}
```



#### 4.4、主启动类

```
@SpringBootApplication
@EnableDiscoveryClient
public class OrderConsulMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderConsulMain80.class, args);
    }
}
```



#### 4.5、业务类

##### 4.5.1、配置类

```
@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
```



##### 4.5.2、Controller

```
@RestController
@Slf4j
public class OrderConsulController {

    public static final String INVOKE_URL = "http://cloud-provider-payment";
    @Resource
    private RestTemplate restTemplate;


    /**
     * http://localhost/consumer/payment/consul
     *
     * @return
     */
    @GetMapping("/consumer/payment/consul")
    public String paymentInfo() {
        return restTemplate.getForObject(INVOKE_URL + "/payment/consul", String.class);
    }
}
```



#### 4.6、测试

访问服务：http://localhost/consumer/payment/consul

### 5、三个注册中心的异同点

#### 5.1、CAP

- C：Consistency(强一致性)

- A：Availability(可用性)

- P：Parrent tolerance(分区容错性)

  

#### 5.2、经典CAP图

- AP：eureka
- CP：zookeeper、Consul

