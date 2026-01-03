package br.com.everson.gestaopedidos.service;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String mensagem() {
        return "Spring Boot está vivo e operando.";
    }
}
