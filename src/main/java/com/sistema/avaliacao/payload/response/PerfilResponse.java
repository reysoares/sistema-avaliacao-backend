package com.sistema.avaliacao.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class PerfilResponse<T> {
    private T publico;
    private Map<String, Object> privado;
    private boolean isDono;

    public PerfilResponse(T publico, Map<String, Object> privado, boolean isDono) {
        this.publico = publico;
        this.privado = privado;
        this.isDono = isDono;
    }
}