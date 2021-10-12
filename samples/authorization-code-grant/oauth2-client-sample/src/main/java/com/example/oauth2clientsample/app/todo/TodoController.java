package com.example.oauth2clientsample.app.todo;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.oauth2clientsample.domain.model.Todo;
import com.example.oauth2clientsample.domain.service.todo.TodoService;

@Controller
@RequestMapping("todo")
public class TodoController {
    @Inject // (1)
    TodoService todoService;

    @GetMapping("/") // (3)
    public String home(Model model) {
        return "todo/home"; // (5)
    }

    @GetMapping("list")
    public String list(Model model) {
    	List<Todo> todos = todoService.getTodoList();
    	model.addAttribute("todos", todos);
        return "todo/list";
    }

}
