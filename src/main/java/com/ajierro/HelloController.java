package com.ajierro;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/v1")
public class HelloController {
    @Get("/types")
    @Produces(MediaType.APPLICATION_JSON) // <3>
    public String index() {
        return "Hello World"; // <4>
    }
}