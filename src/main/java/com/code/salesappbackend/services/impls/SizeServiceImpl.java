package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.Size;
import com.code.salesappbackend.repositories.SizeRepository;
import com.code.salesappbackend.services.interfaces.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SizeServiceImpl extends BaseServiceImpl<Size, Long> implements SizeService {
    private SizeRepository sizeRepository;
    public SizeServiceImpl(JpaRepository<Size, Long> repository) {
        super(repository);
    }
    @Autowired
    public void setSizeRepository(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    public void existsByTextSize(String textSize) throws DataExistsException {
        if(sizeRepository.existsByTextSize(textSize)) {
            throw new DataExistsException("text size is exists");
        }
    }

    @Override
    public void existsByNumberSize(Short numberSize) throws DataExistsException {
        if(sizeRepository.existsByNumberSize(numberSize)) {
            throw new DataExistsException("number size is exists");
        }
    }
}
