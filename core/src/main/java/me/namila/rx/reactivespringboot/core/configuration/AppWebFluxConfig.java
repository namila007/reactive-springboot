//package me.namila.rx.reactivespringboot.core.configuration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
//import org.springframework.http.codec.ServerCodecConfigurer;
//import org.springframework.http.codec.json.Jackson2JsonDecoder;
//import org.springframework.http.codec.json.Jackson2JsonEncoder;
//import org.springframework.web.reactive.config.EnableWebFlux;
//import org.springframework.web.reactive.config.WebFluxConfigurationSupport;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//import org.springframework.web.reactive.config.WebFluxConfigurerComposite;
//import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
//
//@EnableWebFlux
//@Configuration
//public class AppWebFluxConfig implements WebFluxConfigurer {
//    @Override
//    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
//        ReactivePageableHandlerMethodArgumentResolver resolver = new ReactivePageableHandlerMethodArgumentResolver();
//        resolver.setFallbackPageable(PageRequest.of(0,5));
//        configurer.addCustomResolver(resolver);
//    }
//
//    @Bean
//    Jackson2JsonEncoder jackson2JsonEncoder(ObjectMapper mapper){
//        return new Jackson2JsonEncoder(mapper);
//    }
//
//    @Bean
//    Jackson2JsonDecoder jackson2JsonDecoder(ObjectMapper mapper){
//        return new Jackson2JsonDecoder(mapper);
//    }
//
//    @Bean
//    WebFluxConfigurer webFluxConfigurer(Jackson2JsonEncoder encoder, Jackson2JsonDecoder decoder){
//        return new WebFluxConfigurer() {
//            @Override
//            public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
//                configurer.defaultCodecs().jackson2JsonEncoder(encoder);
//                configurer.defaultCodecs().jackson2JsonDecoder(decoder);
//            }
//        };
//    }
//}
