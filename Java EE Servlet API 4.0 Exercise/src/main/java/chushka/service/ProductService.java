package chushka.service;

import chushka.domain.models.ProductModel;

import java.util.List;

public interface ProductService {
    void saveProduct(ProductModel productModel);
    List<ProductModel> getAllProducts();
    ProductModel getProductModelByName(String name);
}
