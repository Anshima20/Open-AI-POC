package com.open.ai.poc.OpenAI.Controller;

import com.open.ai.poc.OpenAI.model.Product;
import com.open.ai.poc.OpenAI.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductController controller;

    @Test
    @DisplayName("Should save the product and return the saved product")
    void addProductAndReturnSavedProduct() {
        Product product = new Product(1, "Test Product", 10, 100.0);
        when(service.saveProduct(product)).thenReturn(product);

        Product savedProduct = controller.addProduct(product);

        assertNotNull(savedProduct);
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        verify(service, times(1)).saveProduct(product);
    }
}