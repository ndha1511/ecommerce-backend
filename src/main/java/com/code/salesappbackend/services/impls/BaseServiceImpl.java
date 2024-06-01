package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.services.interfaces.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {
    private final JpaRepository<T, ID> repository;

    public BaseServiceImpl(JpaRepository<T, ID> repository) {
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
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public T update(ID id, T t) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found data"));
        return repository.save(t);
    }

    @Override
    public T updatePatch(ID id, Map<String, ?> data) {
        T t = repository.findById(id).orElseThrow();
        Class<?> clazz = t.getClass();
        Set<String> keys = data.keySet();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            for(String key : keys) {
                if(method.getName().equals("set" + toUpperCaseFirstChar(key)) ||
                        method.getName().equals("is" + toUpperCaseFirstChar(key))) {
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

    private String toUpperCaseFirstChar(String str) {
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }
}
