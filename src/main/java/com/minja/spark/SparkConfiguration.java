package com.minja.spark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spark.Spark;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Konfigurator koji "ugrađuuje" SparkJava u Spring Boot aplikaciju. 
 * Potrebno je da se isključi jetty ili tomcat od Spring Boot-a, a to se radi u applicaion.properties ovako:
 * <code>spring.main.web-application-type=none</code>
 * Pokupio ideju od:
 * @see <a href="https://github.com/pmackowski/sparkjava-spring-boot-starter">SparkJava Spring Boot Starter Github repo</a>
 */
@Configuration
public class SparkConfiguration {

    @Autowired(required = false)
    private List<ISpark> sparks = new ArrayList<>();

    @Autowired
    ObjectMapper om;

    @Bean
    CommandLineRunner sparkRunner() {
        // UGASIO SPRING BOOT-ov INTERNI WEB SERVER u application.properties (spring.main.web-application-type=none)
        Spark.port(80);
        Spark.exception(Exception.class, (e, req, res) -> {
            System.out.println("%%%%%%%%%%%%%%%%%%%%% ERROR %%%%%%%%%%%%%%%%%%%%%" + e.getMessage());
            res.status(500);
            try {
                res.body(om.writeValueAsString(new ErrorMessage(e.getMessage())));
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        });
        try {
            File f = new File("./spark-spring/target/classes/static");
            System.out.println(f.getCanonicalPath());
            Spark.externalStaticFileLocation(f.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return args -> sparks.stream().forEach( spark -> spark.register() );
    }
    class ErrorMessage {
        private String message;

        public ErrorMessage() {

        }

        public ErrorMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
        @Override
        public String toString() {
            return message;
        }
    }
}