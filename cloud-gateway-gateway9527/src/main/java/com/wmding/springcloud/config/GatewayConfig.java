package com.wmding.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 明月
 * @version 1.0
 * @date 2020-03-18 21:15
 * @description: 网关配置
 */
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
