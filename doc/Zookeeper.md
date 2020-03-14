Zookeeper

### 1、注册中心 Zookeeper

Zookeeper 是一个分布式协调工具，可以实现注册中心功能，取代 Eureka 服务器，zk 作为服务注册中心。


- 注意
关闭 Linux 服务器防火墙后启动 Zookeeper 服务器

```
linux 关闭防火墙
systemctl stop firewalld
systemctl status firewalld
```


### 2、服务提供者

#### 2.1、创建项目

新建cloud-provider-payment8004

#### 2.2、POM

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

    <artifactId>cloud-provider-payment8004</artifactId>
    <description>Zookeeper服务提供者</description>

    <dependencies>
        <dependency>
            <groupId>com.wmding.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>


        <!-- SpringBoot整合Zookeeper客户端  -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
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
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>

```



#### 2.3、YML

```
server:
  #8004表示注册到zookeeper服务器的支付服务提供者端口号
  port: 8004

spring:
  application:
    #服务别名---注册zookeeper到注册中心的名称
    name: cloud-provider-payment

  cloud:
    zookeeper:
      # 默认localhost:2181
      connect-string: localhost:2181 
```



#### 2.4、主启动类

添加注解
> @EnableDiscoveryClient



#### 2.5、Controller

```Java
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "payment/zk")

    public String paymentZk() {
        return "SpringCloud with zookeeper:" + serverPort + " \t " + UUID.randomUUID().toString();
    }
}
```

#### 2.6、启动 8004 注册进 zk

1、启动 zk

> zkServer.sh start

2、启动后问题

解决zookeeper版本jar包冲突问题，修改pom.xml

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

    <artifactId>cloud-provider-payment8004</artifactId>
    <description>Zookeeper服务提供者</description>

    <dependencies>
        <dependency>
            <groupId>com.wmding.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>


        <!-- SpringBoot整合Zookeeper客户端  -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
            <exclusions>
                <!--先排除自带的zookeeper3.5.3-->
                <exclusion>
                    <groupId>org.apache.zookeeper</groupId>
                    <artifactId>zookeeper</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!--添加zookeeper3.4.9版本-->
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.9</version>
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
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>

```


#### 2.7、测试

访问地址，进行测试 http://localhost:8004/payment/zk

#### 2.8、思考

服务节点是临时节点还是持久节点？--->临时节点



### 3、服务消费者


#### 3.1、创建项目

新建cloud-consumerzk-order80

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

    <artifactId>cloud-consumerzk-order80</artifactId>
    <description>订单消费者注册中心 zookeeper</description>

    <dependencies>
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
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>

            <!--排除SpringBoot整合的zookeeper整合的客户端-->
            <exclusions>
                <exclusion>
                    <groupId>org.apache.zookeeper</groupId>
                    <artifactId>zookeeper</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!--添加zookeeper3.4.9版本-->
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.9</version>
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
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```

#### 3.3、YML

```
server:
  port: 80

spring:
  application:
    # 服务别名
    name: cloud-consumer-order

  cloud:
    zookeeper:
      # 注册到zk地址
      connect-string: localhost:2181
```

#### 3.4、主启动类

```
@SpringBootApplication
@EnableDiscoveryClient
public class Order80Main {
    public static void main(String[] args) {
        SpringApplication.run(Order80Main.class, args);
    }
}
```

#### 3.5、业务类
- 配置bean

```Java
@Configuration
public class ApplicationContextConfig {
    
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

- Controller

```Java
@RestController
public class OrderZkController {

    public static final String INVOKE_URL = "http://cloud-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    /**
     * http://localhost/consumer/payment/zk
     */
    @GetMapping("/consumer/payment/zk")
    public String paymentInfo() {
        return restTemplate.getForObject(INVOKE_URL + "/payment/zk", String.class);
    }
}
```

#### 3.6、测试

http://localhost/consumer/payment/zk

