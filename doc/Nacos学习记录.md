SpringCloud Alibaba Nacos 服务注册和配置中心

### 1、Nacos 简介



#### 1.1、为什么叫Nacos

前四个字母为Nameing和Configuration的前两个字母,最后的s为Service

#### 1.2、是什么

- 一个更易于构建原生应用的动态服务发现、配置管理和服务管理平台
- Nacos:Dynamic Naming and Configuration Service
- Nacos就是注册中心+配置中心的组合--》等价于Nacos=Eureka+Config+Bus

#### 1.3、能干什么

- 替代Eureka做服务注册中心
- 替代Config做服务配置中心



#### 1.4、官网



- https://github.com/alibaba/Nacos
- https://nacos.io/zh-cn/

#### 1.5、各种注册中心对比

| 服务注册与发现框架 | CAP模型 | 控制台管理 | 社区活跃度 |
| ------------------ | ------- | ---------- | ---------- |
| Eureka             | AP      | 支持       | 低         |
| Zookeeper          | CP      | 不支持     | 中         |
| Consul             | CP      | 支持       | 高         |
| Nacos              | AP      | 支持       | 高         |

据说 Nacos 在阿里巴巴内部有超过 10 万的实例运行，已经过了类似双十一的流量考验。



### 2、Nacos 安装、运行

#### 2.1、下载 Nacos

https://github.com/alibaba/nacos/releases

#### 2.2、运行

> ```shell
> cd nacos/bin
> sh startup.sh -m standalone
> ```

#### 2.3、访问

http://localhost:8848/nacos/

### 3、Nacos 作为服务注册中心



#### 3.1、官方文档

https://spring-cloud-alibaba-group.github.io/github-pages/greenwich/spring-cloud-alibaba.html#_spring_cloud_alibaba_nacos_discovery



#### 3.2、基于 Nacos 的服务提供者



##### 3.2.1、新建 Module

cloudalibaba-provider-payment9001

##### 3.2.2、POM

父POM

```xml
<!--Spring cloud alibaba 2.1.0.RELEASE-->
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-alibaba-dependencies</artifactId>
  <version>${spring.cloud.alibaba.version}</version>
  <type>pom</type>
  <scope>import</scope>
</dependency>
```



本模块POM

```xml
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

    <artifactId>cloudalibaba-provider-payment9001</artifactId>

    <dependencies>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
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

##### 3.2.3、 YML

```yaml
server:
  port: 9001

spring:
  application:
    name: nacos-payment-provider
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
```

##### 3.2.4、主启动类

```Java
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain9001.class,args);
    }
}
```

##### 3.2.5、业务类

Controller

```Java
@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id) {
        return "nacos register, serverport=" + serverPort + "\t id:" + id;
    }
}
```

##### 3.2.6、测试

- 访问 http://localhost:9001/payment/nacos/1

- 访问 nacos 控制台

  在服务列表中已经显示

#### 3.3、基于 Nacos 的服务消费者

##### 3.3.1、新建 Module

cloudalibaba-consumer-nacos-order83

##### 3.3.2、POM

```xml
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

    <artifactId>cloudalibaba-consumer-nacos-order83</artifactId>


    <dependencies>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
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



##### 3.3.3、 YML

```yml
server:
  port: 83

spring:
  application:
    name: nacos-order-consumer
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848

#消费者将要去访问的微服务名称（注册成功进nacos的微服务提供者）
service-url:
  nacos-user-service: http://nacos-payment-provider
```

##### 3.3.4、主启动类

```Java
@SpringBootApplication
public class Order83Main {
    public static void main(String[] args) {
        SpringApplication.run(Order83Main.class, args);
    }
}
```



##### 3.3.5、业务类

1、Controller

```java
@RestController
@Slf4j
public class OrderNacosController {

    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverUrl;

    @GetMapping("/consumer/payment/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Integer id){
        return restTemplate.getForObject(serverUrl + "/payment/nacos/" + id, String.class);
    }
}
```



2、配置

```java
@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

##### 3.3.6、测试

- 在 nacos 控制台上服务列表中已经显示
- 访问服务。http://localhost:83/consumer/payment/nacos/13

### 4、Nacos 作为服务配置中心

#### 4.1、Nacos作为配置中心-基础配置

##### 4.1.1、新建 Module

cloudalibaba-config-nacos-client3377

##### 4.1.2、POM

```xml
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

    <artifactId>cloudalibaba-config-nacos-client3377</artifactId>

    <dependencies>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
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



##### 4.1.3、YML

application.yml

```yaml
spring:
  profiles:
    active: dev # 开发环境
#    active: test # 测试环境
#    active: info # 开发环境
```

bootstrap.yml

```
server:
  port: 3377

spring:
  application:
    name: nacos-config-client

  cloud:
    nacos:
      discovery:
        # 注册中心
        server-addr: localhost:8848
      config:
        server-addr: localhost:8848 # 配置中心
        file-extension: yaml # 这里指定的文件格式需要和nacos上新建的配置文件后缀相同，否则读不到
```



##### 4.1.4、主启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
public class Config3377Main {
    public static void main(String[] args) {
        SpringApplication.run(Config3377Main.class, args);
    }
}
```

##### 4.1.5、业务类

ConfigClientController

@RefreshScope

```Java
@RestController
@RefreshScope // 支持nacos的动态刷新
public class ConfigClientController {
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/config/info")
    public String getConfigInfo(){
        return configInfo;
    }
}
```



##### 4.1.6、在 Nacos 中添加配置信息

Nacos界面配置对应中需要设置DataId

- 公式:${prefix}-${spring.profile.active}.${file-extension}
- prefix 默认为 spring.application.name 的值
- spring.profile.active 即为当前环境对应的 profile
- file-exetension 为配置内容的数据格式，可以通过配置项 spring.cloud.nacos.config.file-extension 来配置



在配置列表中添加一个配置项， nacos-config-client-dev.yaml

```
config:
    info: "1222"
```



##### 4.1.7、测试

- 启动前需要在nacos客户端-配置管理-配置管理栏目下有对应的yaml配置文件
- 运行cloud-config-nacos-client3377的主启动类
- 访问服务 http://localhost:3377/config/info
- 修改下Nacos中的yaml配置文件,再次调用查看配置的接口,就会发现配置已经刷新

#### 4.2、Nacos作为配置中心-分类配置



三种方式加载配置

##### 4.2.1DataID方案 

- 指定spring.profile.active和配置文件的DataID来使不同环境下读取不同的配置
- 默认空间+默认分组+新建dev和test两个DataId（新建dev配置DataId、新建test配置DataId）

- 通过spring.profile.active属性就能进行多环境下配置文件的读取

  

##### 4.2.2、Group方案

- 通过Group实现环境区分
- 在nacos图形化界面控制台上面新建配置文件DataID
- bootstrap+application 在config下增加一条Group的配置即可配置为DEV_GROUP或TEST_GROUP

##### 4.2.3、Namespace方案

- 新建dev/test/的Namespace
- 回到服务管理-服务列表查看，会区分不同的环境
- 按照域名配置填写
- YML中指定namespace

### 5、Nacos 集群和持久化配置

https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html

部署说明：

https://nacos.io/zh-cn/docs/deployment.html



#### 5.1、持久化配置

- Nacos默认自带的是嵌入式数据derby
- derby到mysql切换步骤
- 启动Nacos,可以看到是个全新的空记录界面,以前是记录在derby



#### 5.2、Linux上Nacos+MySQL生产环境配置

预计需要,1个Nginx+3个nacos注册中心+1个mysql（生产中需保证高可用）

集群配置步骤（重点）可参考 Nacos 文档 https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html

- 1.Linux服务器上mysql数据库配置 sql 语句源文件-》nacos-mysql.sql

- 2.application.properties配置

- 3.Linux服务器上nacos的集群配置cluster.conf

- 4.编辑Nacos的启动脚本startup.sh,使它能够接受不同的启动端口

- 5.Nginx的配置,由它作为负载均衡器

- 6.截止到此处,1个Nginx+3个nacos注册中心+1个mysql

