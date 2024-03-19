package com.di.distudy.api.controller;

import com.di.distudy.api.service.Service;
import com.di.distudy.api.service.TestService;
import com.di.distudy.config.AlphabetConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

//    @Value("${my.name}")
//    private String name;

//    // 생성자 기반 의존성 주입
//    private final TestService testService;
//
//    public TestController(TestService testService) {
//        this.testService = testService;
//    }

    @Autowired
    @Qualifier("sampleService")
    private Service service;

    private final AlphabetConfigs alphabetConfigs;

    public TestController(AlphabetConfigs alphabetConfigs) {
        this.alphabetConfigs = alphabetConfigs;
    }

    @GetMapping("/api/v1/test")
    public String test() {
        return service.getTest();
    }

}
