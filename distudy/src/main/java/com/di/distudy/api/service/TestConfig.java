package com.di.distudy.api.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 자바 설정 방식으로 빈 등록
@Configuration
public class TestConfig {

    @Bean
    public TestService testService() {
        return new TestService();
    }

    @Bean
    public SampleService sampleService() {
        return new SampleService();
    }

}
