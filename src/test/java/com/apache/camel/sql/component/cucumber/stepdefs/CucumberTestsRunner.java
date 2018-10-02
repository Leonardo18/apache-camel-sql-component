package com.apache.camel.sql.component.cucumber.stepdefs;

import com.apache.camel.sql.component.ApacheCamelSqlComponentApplication;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "com.apache.camel.sql.component.cucumber")
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = ApacheCamelSqlComponentApplication.class)
public class CucumberTestsRunner { }
