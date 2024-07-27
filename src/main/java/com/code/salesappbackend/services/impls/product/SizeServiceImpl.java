package com.code.salesappbackend.services.impls.product;

import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.product.Size;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.product.SizeRepository;
import com.code.salesappbackend.services.impls.BaseServiceImpl;
import com.code.salesappbackend.services.interfaces.product.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SizeServiceImpl extends BaseServiceImpl<Size, Long> implements SizeService {
    private SizeRepository sizeRepository;
    public SizeServiceImpl(BaseRepository<Size, Long> repository) {
        super(repository, Size.class);
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
