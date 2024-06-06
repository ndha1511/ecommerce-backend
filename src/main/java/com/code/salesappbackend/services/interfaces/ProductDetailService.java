package com.code.salesappbackend.services.interfaces;
import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.Color;
import com.code.salesappbackend.models.Product;
import com.code.salesappbackend.models.ProductDetail;
import com.code.salesappbackend.models.Size;

public interface ProductDetailService extends BaseService<ProductDetail, Long> {
    void existByAll(Color color, Size size, Product product) throws DataExistsException;
}
