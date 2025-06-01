package com.sistema.avaliacao.payload.response;

import java.util.List;

public interface PaginatedResponse<T> {
    List<T> getContent();
    Integer getPageNumber();
    Integer getPageSize();
    Long getTotalElements();
    Integer getTotalPages();
    boolean isLastPage();
}