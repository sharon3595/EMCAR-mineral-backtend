package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.presentation.response.pojo.ResponsePojo;

import java.util.List;

public interface CRUDService<T, ID> {
    T create(T t);
    T update(ID id, T t) throws Exception;
    List<T> getAll();
    T getById(ID id);
    void delete(ID id);
    ResponsePojo existsById(ID id);
}