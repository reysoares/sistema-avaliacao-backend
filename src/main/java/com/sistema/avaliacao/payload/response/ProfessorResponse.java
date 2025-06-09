package com.sistema.avaliacao.payload.response;

import java.util.List;
import com.sistema.avaliacao.payload.dto.ProfessorDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorResponse implements PaginatedResponse<ProfessorDTO>{


    private List<ProfessorDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;

}
