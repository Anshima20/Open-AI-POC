package com.open.ai.poc.OpenAI.service;

import com.open.ai.poc.OpenAI.model.Product;
import com.open.ai.poc.OpenAI.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Should return null when the product id is not found")
    void getProductByIdWhenIdNotFound() {
        int id = 1;
        when(productRepository.findById(id)).thenReturn(null);

        Product product = productService.getProductById(id);

        assertEquals(null, product);
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return the product with the given id")
    void getProductByIdWhenIdExists() { // create a product with id 1
        Product product = new Product(1, "Test Product", 10, 100.0);

        // mock the behavior of the repository's findById method
        when(productRepository.findById(1)).thenReturn(product);

        // call the getProductById method of the service
        Product result = productService.getProductById(1);

        // verify that the repository's findById method was called once with the correct id
        verify(productRepository, times(1)).findById(1);

        // assert that the returned product is the same as the one we created
        assertEquals(product, result);
    }

    @Test
    @DisplayName("Should save the product and return the saved product")
    void saveProductAndReturnSavedProduct() { // create a new product
        Product product = new Product(1, "Test Product", 10, 100.0);

        // mock the behavior of the repository's save method
        when(productRepository.save(product)).thenReturn(product);

        // call the method under test
        Product savedProduct = productService.saveProduct(product);

        // verify that the repository's save method was called once
        verify(productRepository, times(1)).save(product);

        // verify that the saved product is the same as the original product
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Should return all products")
    void getProductsReturnsAllProducts() {
        List<Product> products = Arrays.asList(new Product(1, "Product 1", 10, 100.0),
                                               new Product(2, "Product 2", 20, 200.0),
                                               new Product(3, "Product 3", 30, 300.0));
        when(productRepository.getAllProducts()).thenReturn(products);

        List<Product> result = productService.getProducts();

        assertEquals(products.size(), result.size());
        assertEquals(products.get(0).getId(), result.get(0).getId());
        assertEquals(products.get(1).getName(), result.get(1).getName());
        assertEquals(products.get(2).getQuantity(), result.get(2).getQuantity());

        verify(productRepository, times(1)).getAllProducts();
    }
}