package br.com.everson.gestaopedidos.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiError {

    private int status;
    private String erro;
    private LocalDateTime timestamp;
    private Map<String, String> campos;

    public ApiError(int status, String erro, Map<String, String> campos) {
        this.status = status;
        this.erro = erro;
        this.campos = campos;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getErro() {
        return erro;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getCampos() {
        return campos;
    }
}