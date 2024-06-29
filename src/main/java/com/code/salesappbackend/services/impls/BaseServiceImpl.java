package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.dtos.responses.PageResponse;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.customizations.BaseCustomizationRepository;
import com.code.salesappbackend.services.interfaces.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class BaseServiceImpl<T, ID extends Serializable> extends BaseCustomizationRepository<T> implements BaseService<T, ID> {
    private final BaseRepository<T, ID> repository;

    public BaseServiceImpl(BaseRepository<T, ID> repository, Class<T> entityClass) {
        super(entityClass);
        this.repository = repository;
    }

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable, Specification<T> specification) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public T update(ID id, T t) throws DataNotFoundException {
        repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("not found data"));
        return repository.save(t);
    }



    @Override
    public T updatePatch(ID id, Map<String, ?> data) throws DataNotFoundException {
        T t = repository.findById(id).orElseThrow(
                () -> new DataNotFoundException("not found data"));
        Class<?> clazz = t.getClass();
        Set<String> keys = data.keySet();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            for(String key : keys) {
                if(method.getName().equals("set" + toUpperCaseFirstChar(key))) {
                    try {
                        Object value = data.get(key);
                        if (value instanceof String && method.getParameterTypes()[0].isEnum()) {
                            value = Enum.valueOf((Class<Enum>) method.getParameterTypes()[0], (String) value);
                        }
                        method.invoke(t, value);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return repository.save(t);
    }

    @Override
    public PageResponse<?> getPageData(int pageNo, int pageSize, String[] search, String[] sort)  {
        return super.getPageData(pageNo, pageSize, search, sort);
    }

    private String toUpperCaseFirstChar(String str) {
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }
}
