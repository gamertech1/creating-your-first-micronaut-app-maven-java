package example.micronaut.controller;

import example.micronaut.service.HelloService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

@Controller()
public class HelloController {

    @Inject
    HelloService helloService;

    @Get("/hello")
    public String hello() {
        return helloService.getMessage();
    }

    @Get("/hello/greet")
    public String helloWithName(@QueryValue String name) {
        return "Hello, " + name + "!";
    }
}

