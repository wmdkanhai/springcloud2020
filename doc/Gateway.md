Gateway

### 1、简介

#### 1.1、官网介绍

- 上一代 Zuul https://github.com/Netflix/zuul/wiki
- 当前 gateway https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.1.RELEASE/reference/html/

#### 1.2、是什么

Gateway 是 Spring 生态系统之上构建的 API 网关服务，基于 Spring5，Spring Boot 2 和 Project Reactor等技术。Gateway 旨在提供一个简单而有效的方式来对API进行路由，以及提供一些强大的过滤功能，例如：熔断、限流、重试等。

Gateway使用的是Webflux中的reactor-netty响应式编程组件,底层使用了Netty通讯框架

WebFlux是什么？

> 传统的 web 架构，比如说：Struts2，SpringMVC等都是基于 Servlet API 与 Servlet 容器基础上运行的。但是在 Servlet 3.1之后就有了异步非阻塞的支持。而WebFlux 是一个典型的非阻塞异步的框架，它的核心是基于 Reactor 的相关 API 实现的，相对于传统的web框架来说，它可以运行在诸如 Netty，Undertow 以及支持 Servlet 3.1的容器上。



Spring webFlux 是 Spring 5.0 引入的新的响应式框架，区别于 SpringMVC。它不需要依赖 Servlet API，他是完全异步非阻塞的并基于 Reactor 来实现的响应式流规范。



#### 1.3、能干什么

- 反向代理
- 鉴权
- 流量控制
- 日志监控

### 2、三大核心概念

#### 2.1、Route(路由)

路由是构建网关的基本模块,它由ID,目标URI,一系列的断言和过滤器组成,如断言为true则匹配该路由

#### 2.2、Predicate（断言）

参考的是Java8的java.util.function.Predicate

开发人员可以匹配HTTP请求中的所有内容(例如请求头或请求参数),如果请求与断言相匹配则进行路由

#### 2.3、Filter（过滤）

指的是Spring框架中GatewayFilter的实例,使用过滤器,可以在请求被路由前或者之后对请求进行修改.

#### 2.4、总结

Web 请求，通过一些配匹条件，定位到真正的服务节点，并在这个转发过程的前后，进行一些精细化的控制，Predicate 就是我们需要匹配的条件，而filter，可以理解为一个无所不能的拦截器，有了这两个元素，再加上目标 uri，就可以实现一个具体的路由了。

### 3、Gateway 工作流程

客户端想 Spring Cloud Gateway发出请求，然后在 Gateway Handler Mapping中找到与请求匹配的路由，将其发送到 Gateway web Handler。

Handler再通过指定的过滤器链来将请求发送到我们时间的服务执行业务逻辑，然后返回。

过滤器之间用虚线分开是因为过滤器可能会在发送代理请求前（pre）或之后（post）执行业务逻辑

Filter 在 pre 类型的过滤器可以做参数校验，权限控制，流量监控，日志输出，协议转换能

在 post 类型的过滤器中可以做响应内容，响应头的修改，日志的输出，流量监控等。



### 4、入门配置

#### 4.1、新建 Module

cloud-gateway-gateway9527

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

    <artifactId>cloud-gateway-gateway9527</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>

        <!--gateway无需web和actuator-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <dependency>
            <groupId>com.wmding.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

    </dependencies>

</project>
```



#### 4.3、YML

```
server:
  port: 9527
spring:
  application:
    name: cloud-gateway

  cloud:
    gateway:
      discovery:
        locator:
          enabled: true # 开启从注册中心动态创建路由的功能，利用微服务名称j进行路由
      routes:
        - id: payment_route # 路由的id,没有规定规则但要求唯一,建议配合服务名
          #匹配后提供服务的路由地址
          uri: http://localhost:8001
          predicates:
            - Path=/payment/get/** # 断言，路径相匹配的进行路由
            #- After=2017-01-20T17:42:47.789-07:00[America/Denver]
            #- Before=2017-01-20T17:42:47.789-07:00[America/Denver]
            #- Cookie=username,zzyy
            #- Header=X-Request-Id, \d+ #请求头要有X-Request-Id属性，并且值为正数
            #- Host=**.atguigu.com
            #- Method=GET
            #- Query=username, \d+ # 要有参数名username并且值还要是正整数才能路由
          # 过滤
          #filters:
          #  - AddRequestHeader=X-Request-red, blue
        - id: payment_route2
          uri: http://localhost:8001
          predicates:
            Path=/payment/lb/** #断言,路径相匹配的进行路由

eureka:
  instance:
    hostname: cloud-gateway-service
  client:
    fetch-registry: true
    register-with-eureka: true
    service-url:
      defaultZone: http://localhost:7001/eureka/
```



#### 4.4、主启动类

```
@SpringBootApplication
@EnableEurekaClient
public class GatewayMain9527 {

    public static void main(String[] args) {
        SpringApplication.run(GatewayMain9527.class, args);
    }
}
```



#### 4.5、9527网关如何做路由映射呢???

cloud-provider-payment8001看看controller的访问地址，我们目前不想暴露8001端口,希望在8001外面套一层9527

#### 4.6、测试

启动 7001 Eureka

启动 8001

启动 9527

##### 访问说明

- 添加网关前 http://localhost:8001/payment/get/31
- 添加网关后 http://localhost:9527/payment/get/31

#### 4.7 Gateway网关路由有两种配置方式

- 在配置文件yaml中配置
- 代码中注入RouteLocator的Bean

```
@Configuration
public class GatewayConfig {

    /**
     * 配置了一个id为route-name的路由规则
     * 当访问localhost:9527/guonei的时候，将会转发至https://news.baidu.com/guonei
     *
     * @param routeLocatorBuilder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        return routes.route("path_route_wmding", r -> r.path("/guonei").uri("https://news.baidu.com/guonei")).build();
    }
}
```



### 5、通过服务名实现动态

默认情况下Gatway会根据注册中心注册的服务列表, 以注册中心上微服务名为路径创建动态路由进行转发,从而实现动态路由的功能

### 6、Predicate

Predicate就是为了实现一组匹配规则, 让请求过来找到对应的Route进行处理

### 7、Filter的使用

路由过滤器可用于修改进入的 HTTP 请求和返回的 HTTP 响应，路由过滤器之鞥呢指定路由进行使用。

SpringCloud Gateway 中内置了多重路由过滤器，都是由 GatewayFilter 的工厂类来产生的。

#### 7.1、生命周期

- pre
- post



#### 7.2、种类

- GatewayFilter https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.1.RELEASE/reference/html/#gatewayfilter-factories
- GlobalFilter https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.1.RELEASE/reference/html/#global-filters



#### 7.3、自定义过滤器

自定义全局GlobalFilter

```
/**
 * 全局自定义过滤器
 * @author 明月
 * @version 1.0
 * @date 2020-03-18 21:04
 * @description: 设置一个全局的过滤器，如果当前请求不携带某个参数时，就不能通过
 */
@Component
@Slf4j
public class MyLogGatewayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("come in global filter: {}",new Date());

        ServerHttpRequest request = exchange.getRequest();
        String uname = request.getQueryParams().getFirst("uname");

        if (StringUtils.isBlank(uname)){

            log.info("用户名为空，非法用户");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }

        // 放行
        return chain.filter(exchange);
    }

    /**
     * 过滤器的加载顺序，值越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
```



























