package com.mine.manager.parameters.presentation.response.pojo;


import java.time.LocalDateTime;

import com.mine.manager.common.Pojo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Pojo
@Getter
@Setter
@NoArgsConstructor
public class BasePojo {

  private Integer id;

  private Boolean active;

  private String createdBy;

  private String updatedBy;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

}
