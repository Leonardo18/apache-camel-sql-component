package com.apache.camel.sql.component;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@TestConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = ApacheCamelSqlComponentApplication.class)
@ComponentScan(basePackages = {"com.apache.camel.sql.component"})
public class TestConfig { }
