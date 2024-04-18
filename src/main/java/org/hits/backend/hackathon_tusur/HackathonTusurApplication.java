package org.hits.backend.hackathon_tusur;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

@SpringBootApplication
public class HackathonTusurApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HackathonTusurApplication.class)
                .beanNameGenerator(new FullyQualifiedAnnotationBeanNameGenerator())
                .run(args);
    }

}
