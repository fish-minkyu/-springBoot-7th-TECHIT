package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

// @EnableAutoConfiguration: 이 클래스를 Spring Boot로서 자동 설정을 하게끔 해주는 어노테이션
// @ComponentScan: 이 클래스를 기준으로 Bean 객체를 검색하게 하는 어노테이션

// @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// SpringApplication.run(DemoApplication.class, args);
		// 당연한 말이지만, SpringApplication.run(DemoApplication.class, args);을 주석처리하면 애플리케이션이 동작하지 않음


		// 어떤 클래스들이 Bean으로 등록이 되었는지 살펴보기 위한 코드
		// 현재 실행중인 IoC Container를 변환한다.
		ApplicationContext applicationContext // 컨텍스트는 맥락이란 뜻
			= SpringApplication.run(DemoApplication.class, args);

		// IoC Container가 관리하고 있는 Bean 객체들을 확인한다.
		for (String beanName : applicationContext.getBeanDefinitionNames()) {
			System.out.println(beanName); // Bean 객체들 이름들만 출력
			// demoApplication
			// demoController
			// 이 2개의 클래스가 bean으로 등록이 되어 사용할 수 있는 상태가 된 것을 알 수가 있다.
		}

	}

}
