package com.di.distudy.config;

import com.di.distudy.api.service.ModuleB;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(value = {"com.di"}) // 패키지 이름 작성
@ComponentScan(basePackageClasses = {
        ModuleB.class
}) // ModuleB가 포함된 패키지 아래까지
public class Config {
}
