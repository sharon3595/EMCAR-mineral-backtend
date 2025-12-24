package com.mine.manager.parameters.domain.service.Implements;


import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.exception.EntityNotFoundException;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.domain.service.Interfaces.CRUDService;
import com.mine.manager.parameters.presentation.response.pojo.ResponsePojo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class CRUDServiceImpl<T, ID> implements CRUDService<T, ID> {

    protected abstract GenericRepository<T, ID> getRepository();

    @Override
    public T create(T t) {
        return getRepository().save(t);
    }

    @Override
    public T update(ID id, T t) throws Exception {
        Class<?> clazz = t.getClass();
        Method setIdMethod = clazz.getMethod("setId", id.getClass());
        setIdMethod.invoke(t, id);

        T entityGenericFound = this.getById(id);

        Class<?> genericClazz = entityGenericFound.getClass();
        Field field = genericClazz.getField("createdAt");
        field.setAccessible(true);
        Object createdAt = field.get(entityGenericFound);
        Method setCreatedAt = entityGenericFound.getClass().getMethod("setCreatedAt", createdAt.getClass());
        setCreatedAt.invoke(t, createdAt);
        return getRepository().save(t);
    }

    @Override
    public List<T> getAll() {
        return getRepository().findAll();
    }

    @Override
    public T getById(ID id) {
        return getRepository().findById(id).orElseThrow(()-> new EntityNotFoundException(getEntityName(), (Integer) id));
    }

    @Override
    public void delete(ID id) {
        this.getById(id);
        getRepository().deleteById(id);
    }
    @Override
    public ResponsePojo existsById(ID id) {
        this.getById(id);
        return new ResponsePojo(getEntityName() + " con Id " + id + " encontrado", true);
    }
    private String getEntityName() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return SpanishEntityNameProvider.getSpanishName(((Class<?>) parameterizedType.getActualTypeArguments()[0]).getSimpleName());
    }
}