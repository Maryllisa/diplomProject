package com.example.diplomproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchData {

    private String sortCriteria;
    private String howSort;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date  dateTo;
    private String searchQuery;
    private String searchParam;
}
