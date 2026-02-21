package com.mine.manager.parameters.presentation.request.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotFilter {

    private Integer id;
    private String prefix;
    private String description;
    private Integer initialDocNumber;
    private Integer currentDocNumber;
    private String assignment;
    private Boolean state;

}