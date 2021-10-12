package com.example.oauth2clientsample.domain.service.todo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import com.example.oauth2clientsample.domain.model.Todo;

@Service
public class TodoServiceImpl implements TodoService {

    @Inject
    @Named("todoAuthCodeGrantResourceRestTemplate")
    RestOperations restOperations;

    @Value("${resource.serverUrl}/api/v1/todos")
    String url;

    
    @Override
    public List<Todo> getTodoList() {
        Todo[] todoArray = restOperations.
            getForObject(url, Todo[].class);
        return Arrays.asList(todoArray);
    }
    
//    @Override
//    public List<Todo> getTodoList() {
//    	Todo todo = new Todo();
//    	todo.setTodoId("aaa");
//    	todo.setTodoTitle("titleだよ");
//    	List<Todo> list = new ArrayList<>();
//    	
//    	list.add(todo);
//
//        return list;
//    }
}