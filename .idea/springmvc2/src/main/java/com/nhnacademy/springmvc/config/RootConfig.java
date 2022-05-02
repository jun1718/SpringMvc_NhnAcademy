package com.nhnacademy.springmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
@Component(basePackageClasses = Base.class,
excludeFilters = @ComponentScan.Filter(ControllerBase.class))
public class RootConfig implements {

}