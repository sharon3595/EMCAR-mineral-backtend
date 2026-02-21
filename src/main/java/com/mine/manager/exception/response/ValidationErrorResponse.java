package com.mine.manager.exception.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ValidationErrorResponse {

    private int code;
    private String status;
    private String path;
    private List<FieldErrorModel> errors;

}
