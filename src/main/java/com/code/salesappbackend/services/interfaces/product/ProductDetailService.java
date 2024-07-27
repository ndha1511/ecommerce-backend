package com.code.salesappbackend.services.interfaces.product;
import com.code.salesappbackend.exceptions.DataExistsException;
import com.code.salesappbackend.models.product.Color;
import com.code.salesappbackend.models.product.Product;
import com.code.salesappbackend.models.product.ProductDetail;
import com.code.salesappbackend.models.product.Size;
import com.code.salesappbackend.services.interfaces.BaseService;

public interface ProductDetailService extends BaseService<ProductDetail, Long> {
    void existByAll(Color color, Size size, Product product) throws DataExistsException;
}
