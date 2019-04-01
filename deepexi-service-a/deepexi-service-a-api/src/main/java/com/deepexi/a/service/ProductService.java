package com.deepexi.a.service;

import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.a.domain.eo.Product;

import java.util.List;

public interface ProductService {

    PageBean getProductList(Integer page, Integer size, Integer price);

    Product getProductById(String id);

    Integer createProduct(Product product);

    Boolean deleteProductById(String id);

    Boolean bathDeleteProductByIds(List<String> ids);

    Integer updateProduct(Product product);

    void testError();
}
