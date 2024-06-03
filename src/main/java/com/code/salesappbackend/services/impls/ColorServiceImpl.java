package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.models.Color;
import com.code.salesappbackend.services.interfaces.ColorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl extends BaseServiceImpl<Color, Long> implements ColorService {
    public ColorServiceImpl(JpaRepository<Color, Long> repository) {
        super(repository);
    }
}
