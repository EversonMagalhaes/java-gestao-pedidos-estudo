package br.com.everson.gestaopedidos.controller;

import br.com.everson.gestaopedidos.service.HelloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "0. Hello", description = "Estudo de Java com Spring Boot")
public class HelloController {

    private final HelloService helloService;
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    @Operation(summary = "Olá")
    public String hello() {
        return "Spring Boot está vivo e operando!";
    }
}





