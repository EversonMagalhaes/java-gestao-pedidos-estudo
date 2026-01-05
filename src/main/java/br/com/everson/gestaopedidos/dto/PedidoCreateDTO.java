package br.com.everson.gestaopedidos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class PedidoCreateDTO {

    @NotEmpty
    @Valid
    private List<ItemPedidoCreateDTO> itens;

    public List<ItemPedidoCreateDTO> getItens() {
        return itens;
    }
}
