package chushka.service;

import chushka.domain.entities.Product;
import chushka.domain.entities.Type;
import chushka.domain.models.ProductModel;
import chushka.repository.ProductRepository;
import chushka.util.ModelMapper;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Inject
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveProduct(ProductModel productModel) {
        Product product = this.modelMapper.map(productModel, Product.class);
        product.setType(Type.valueOf(productModel.getType()));
        this.productRepository.save(product);
    }

    @Override
    public List<ProductModel> getAllProducts() {
        ProductModel[] productModels = this.modelMapper.map(this.productRepository.findAll(), ProductModel[].class);
        return Arrays.asList(productModels);
    }

    @Override
    public ProductModel getProductModelByName(String name) {
        ProductModel productModel = this.modelMapper.map(this.productRepository.findByName(name),ProductModel.class);
        return productModel;
    }
}
