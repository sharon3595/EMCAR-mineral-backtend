package com.mine.manager.parameters.presentation.request.filter.searchType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonSearchType {

  private String firstName;
  private String secondName;
  private String paternalSurname;
  private String maternalSurname;
  private String documentNumber;
  private String address;
}
