package com.apache.camel.sql.component.cucumber.stepdefs;

import com.apache.camel.sql.component.TestConfig;
import com.apache.camel.sql.component.api.dto.TodoDto;
import com.apache.camel.sql.component.api.dto.TodoResponseDto;
import com.apache.camel.sql.component.cucumber.World;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TodoStepdefs extends TestConfig implements En {

    @Autowired
    private World world;
    @LocalServerPort
    private Integer port;
    private RestTemplate restTemplate;
    @Autowired
    private DataSource dataSource;

    public TodoStepdefs() {
        Before(new String[]{"@Todo"},() ->{
            restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
            ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("createTableContext.sql"));
            ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("insertInitializeData.sql"));
        });

        After(new String[]{"@Todo"},() -> {
            ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("cleanDatabaseContext.sql"));
        });

        Given("^the todo name (.*)$", (String name) -> {
            world.map.put("name", name);
        });

        Given("^the todo without name$", () -> { });

        Given("^the description (.*)$", (String description) -> {
            world.map.put("description", description);
        });

        Given("^without description$", () -> { });

        Given("^the priority (\\d+)$", (Integer priority) -> {
            world.map.put("priority", priority);
        });

        Given("^without priority$", () -> { });

        Given("^the author (.*)$", (String author) -> {
            world.map.put("author", author);
        });

        Given("^without author", () -> { });

        Given("^the todo id (\\d+)$", (Integer id) -> {
            world.map.put("id", id);
        });

        Given("^without todo id", () -> { });

        When("^create a todo$", () -> {
            world.status = 200;
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<TodoDto> entity = new HttpEntity<>(buildTodoDto(), headers);

            try {
                world.map.put("createTodoResponse", restTemplate.exchange(String.format("http://localhost:%s/api/v1/todo", port),
                        HttpMethod.POST,
                        entity,
                        TodoResponseDto.class)
                        .getBody());
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.status = e.getRawStatusCode();
                world.errorMessage = e.getMessage();
            }
        });

        When("^get a todo$", () -> {
            world.status = 200;
            try {
                world.map.put("getTodoResponse", restTemplate.exchange(String.format("http://localhost:%s/api/v1/todo/%s", port, world.map.get("id")),
                        HttpMethod.GET,
                        null,
                        TodoResponseDto.class)
                        .getBody());
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.status = e.getRawStatusCode();
                world.errorMessage = e.getMessage();
            }
        });

        Then("^should return a status code (\\d+)$", (Integer expectedStatus) -> {
            assertEquals(expectedStatus, world.status);
        });

        Then("^return todo created$", () ->{
            assertTrue(Optional.ofNullable((TodoResponseDto) world.map.get("createTodoResponse")).isPresent());
        });

        Then("^return todo founded$", () ->{
            assertTrue(Optional.ofNullable((TodoResponseDto) world.map.get("getTodoResponse")).isPresent());
        });
    }

    private TodoDto buildTodoDto() {
        return new TodoDto(
                (String) world.map.get("name"),
                (String) world.map.get("description"),
                (Integer) world.map.get("priority"),
                (String) world.map.get("author"));
    }
}
