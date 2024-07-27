package com.code.salesappbackend.services.impls.product;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.product.Color;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.product.ColorRepository;
import com.code.salesappbackend.services.impls.BaseServiceImpl;
import com.code.salesappbackend.services.interfaces.product.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl extends BaseServiceImpl<Color, Long> implements ColorService {
    private ColorRepository colorRepository;
    public ColorServiceImpl(BaseRepository<Color, Long> repository) {
        super(repository, Color.class);
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
