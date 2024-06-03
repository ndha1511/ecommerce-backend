package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.models.Size;
import com.code.salesappbackend.services.interfaces.SizeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SizeServiceImpl extends BaseServiceImpl<Size, Long> implements SizeService {
    public SizeServiceImpl(JpaRepository<Size, Long> repository) {
        super(repository);
    }
}
