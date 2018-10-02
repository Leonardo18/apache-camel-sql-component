package com.apache.camel.sql.component.route;

import com.apache.camel.sql.component.config.TodoConfig;
import com.apache.camel.sql.component.model.TodoModel;
import javassist.NotFoundException;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Component
public class TodoRoute  extends RouteBuilder {

    @Autowired
    TodoConfig todoConfig;

    @Override
    public void configure() {
        from("direct:createTodo")
                .routeId("createTodo")
                .log(LoggingLevel.INFO, "Inserting todo data to database. Body ${in.body}")
                .choice()
                    .when(this::isNotDataPresent)
                        .throwException(new NotFoundException("No data present to create"))
                    .otherwise()
                        .convertBodyTo(TodoModel.class)
                        .to(buildUriInsertTodo())
                        .log(LoggingLevel.INFO, "ID ${in.headers.id}")
                        .process(this::setIdTodoModel)
                .endChoice()
        .end();

        from("direct:getTodo")
                .routeId("getTodo")
                .log(LoggingLevel.INFO, "retrieving todo by id ${body}")
                .convertBodyTo(Integer.class)
                .to(buildUriGetTodo())
                .log(LoggingLevel.INFO, "Todo founded ${body}")
                .choice()
                    .when(this::NotFoundTodo)
                        .throwException(new NotFoundException("Todo not found to id informed"))
                .endChoice()
        .end();
    }

    private boolean isNotDataPresent(Exchange exchange) {
        return !Optional.ofNullable(exchange.getIn().getBody(TodoModel.class)).isPresent();
    }

    private boolean NotFoundTodo(Exchange exchange) {
        return !Optional.ofNullable(exchange.getIn().getBody(TodoModel.class)).isPresent();
    }

    private String buildUriInsertTodo() {
        return UriComponentsBuilder
                .newInstance()
                .path("sql:classpath:insertTodo.sql")
                .queryParam("outputHeader", "id")
                .queryParam("outputType", "SelectOne")
                .queryParam("dataSource", "todo")
                .build()
                .toString();
    }

    private String buildUriGetTodo() {
        return UriComponentsBuilder
                .newInstance()
                .path("sql:classpath:getTodo.sql")
                .queryParam("outputType", "SelectOne")
                .queryParam("outputClass", "com.apache.camel.sql.component.model.TodoModel")
                .queryParam("dataSource", "todo")
                .build()
                .toString();
    }

    private void setIdTodoModel(Exchange exchange){
        exchange.getIn().getBody(TodoModel.class).setId(exchange.getIn().getHeader("id", Integer.class));
    }
}
