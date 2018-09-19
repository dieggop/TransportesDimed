package com.br.dieggocarrilho.linhas.utils;

public class MontagemPageRequest {

    public static org.springframework.data.domain.PageRequest getPageRequest(Integer page, Integer perPage) {
        int lPage = 0;
        int lPerPage = 200;
        if (page != null && page > 0) {
            lPage = page - 1;
        }
        if (perPage != null && perPage <= 200 && perPage > 0) {
            lPerPage = perPage;
        }
        return org.springframework.data.domain.PageRequest.of(lPage, lPerPage);
    }
}
