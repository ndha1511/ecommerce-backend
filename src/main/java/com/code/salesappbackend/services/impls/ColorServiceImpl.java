package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.Color;
import com.code.salesappbackend.repositories.ColorRepository;
import com.code.salesappbackend.services.interfaces.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl extends BaseServiceImpl<Color, Long> implements ColorService {
    private ColorRepository colorRepository;
    public ColorServiceImpl(JpaRepository<Color, Long> repository) {
        super(repository);
    }
    @Autowired
    public void setRepository(ColorRepository repository) {
        this.colorRepository = repository;
    }

    @Override
    public void existsByColorName(String colorName) throws DataExistsException {
        if(colorRepository.existsByColorName(colorName)) {
            throw new DataExistsException("color name is exists");
        }
    }
}
