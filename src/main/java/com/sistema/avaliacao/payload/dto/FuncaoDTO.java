package com.sistema.avaliacao.payload.dto;

import com.sistema.avaliacao.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncaoDTO {
    private Long id;
    private Perfil perfil;
}
