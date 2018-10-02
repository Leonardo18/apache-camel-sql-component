package com.apache.camel.sql.component.api;

import com.apache.camel.sql.component.api.dto.TodoDto;
import com.apache.camel.sql.component.api.dto.TodoResponseDto;
import com.apache.camel.sql.component.model.TodoModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.rmi.UnexpectedException;
import java.util.stream.Stream;

@RestController
public class TodoApi extends ApiBase implements BaseVersion {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProducerTemplate producerTemplate;

    @PostMapping("/todo")
    @ApiOperation(
            value = "Create a todo",
            response = TodoDto.class,
            notes = "This operation insert a todo in database"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    code = 200,
                    message = "Success to insert todo in database",
                    response = TodoDto.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "When have some expected error in data",
                    response = Error.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "When have some unexpected error",
                    response = Error.class
            )
    })
    public ResponseEntity<?> createTodo(@Valid @RequestBody TodoDto todoDto) {
        return Stream.of(todoDto)
                .map(this::convertTodoDtoToTodoModel)
                .map(entity -> producerTemplate.requestBody("direct:createTodo", entity, TodoModel.class))
                .map(this::convertTodoModelToTodoResponseDto)
                .map(dto ->
                        created(
                                ServletUriComponentsBuilder
                                        .fromPath("/todo")
                                        .buildAndExpand(dto.getId())
                                        .toUri(),
                                dto
                        )
                )
                .findFirst()
                .get();
    }

    @GetMapping("/todo/{id}")
    @ApiOperation(
            value = "Get a todo",
            response = TodoDto.class,
            notes = "This operation get a todo in database by id"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    code = 200,
                    message = "Success to get todo from database",
                    response = TodoDto.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "When have some expected error in data",
                    response = Error.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "When have some unexpected error",
                    response = Error.class
            )
    })
    public ResponseEntity<?> getTodo(@PathVariable(required = true) Integer id) {
        return Stream.of(id)
                .map(entity -> producerTemplate.requestBody("direct:getTodo", entity, TodoModel.class))
                .map(this::convertTodoModelToTodoResponseDto)
                .map(this::ok)
                .findFirst()
                .get();
    }

    private TodoModel convertTodoDtoToTodoModel(TodoDto todoDto){
        return modelMapper.map(todoDto, TodoModel.class);
    }

    private TodoResponseDto convertTodoModelToTodoResponseDto(TodoModel todoModel){
        return modelMapper.map(todoModel, TodoResponseDto.class);
    }
}
