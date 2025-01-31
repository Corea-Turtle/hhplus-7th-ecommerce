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
                .pathsToMatch("/balance/**") // API endpoint 경로 - 밑에 .addOpenApiCustomizer 추가로 테그 등 추가 가능
                .build();
    }

    @Bean
    public GroupedOpenApi couponGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("coupon")
                .pathsToMatch("/coupon/**")
                .build();
    }

    @Bean
    public GroupedOpenApi paymentGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("payment")
                .pathsToMatch("/payment/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("product")
                .pathsToMatch("/product/**")
                .build();
    }

    @Bean
    public GroupedOpenApi purchaseOrderGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("purchase_order")
                .pathsToMatch("/purchase_order/**")
                .build();
    }

    @Bean
    public GroupedOpenApi stockGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("stock")
                .pathsToMatch("/stock/**")
                .build();
    }

    @Bean
    public GroupedOpenApi staticsGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("statics")
                .pathsToMatch("/statics/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userGroupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("user")
                .pathsToMatch("/user/**")
                .build();
    }
}
