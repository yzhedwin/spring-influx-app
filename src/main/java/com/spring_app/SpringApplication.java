package com.spring_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;

@ComponentScan({"com.spring_app"})
@SpringBootApplication
public class SpringApplication  extends SpringBootServletInitializer {
	@Autowired
	Runnable MessageListener;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringApplication.class);
	}

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }

	@Bean
	public CommandLineRunner schedulingRunner(TaskExecutor executor) {
	    return args -> executor.execute(MessageListener);
	}

}