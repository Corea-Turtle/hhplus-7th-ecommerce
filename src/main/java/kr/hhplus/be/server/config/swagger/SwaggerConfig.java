package kr.hhplus.be.server.config.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi balanceGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("balance") // group 설정 API들을 그룹화
                .pathsToMatch("/v1/balance/**") // API endpoint 경로 - 밑에 .addOpenApiCustomizer 추가로 테그 등 추가 가능
                .build();
    }

    @Bean
    public GroupedOpenApi couponGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("coupon")
                .pathsToMatch("/v1/coupon/**")
                .build();
    }

    @Bean
    public GroupedOpenApi paymentGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("payment")
                .pathsToMatch("/v1/payment/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("product")
                .pathsToMatch("/v1/product/**")
                .build();
    }

    @Bean
    public GroupedOpenApi purchaseOrderGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("purchase_order")
                .pathsToMatch("/v1/purchase_order/**")
                .build();
    }

    @Bean
    public GroupedOpenApi stockGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("stock")
                .pathsToMatch("/v1/stock/**")
                .build();
    }

    @Bean
    public GroupedOpenApi staticsGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("statics")
                .pathsToMatch("/v1/statics/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("user")
                .pathsToMatch("/v1/user/**")
                .build();
    }
}
