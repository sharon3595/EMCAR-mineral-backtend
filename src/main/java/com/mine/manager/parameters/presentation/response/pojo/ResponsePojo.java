package com.mine.manager.parameters.presentation.response.pojo;

import com.mine.manager.common.Pojo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Pojo
@Getter
@Setter
@NoArgsConstructor
public class ResponsePojo {
    private String message;
    private Boolean value;

    public ResponsePojo(String message, Boolean value) {
        this.message = message;
        this.value = value;
    }
}
