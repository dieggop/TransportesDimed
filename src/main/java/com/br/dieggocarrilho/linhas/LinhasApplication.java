package com.br.dieggocarrilho.linhas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        LinhasApplication.class,
})
public class LinhasApplication {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public ResourceBundleMessageSource resourceBundle() {
        ResourceBundleMessageSource rb = new org.springframework.context.support.ResourceBundleMessageSource();
        rb.setBasename("messages");
        return rb;
    }

	public static void main(String[] args) {
		SpringApplication.run(LinhasApplication.class, args);
	}

}
class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LinhasApplication.class);
    }

}