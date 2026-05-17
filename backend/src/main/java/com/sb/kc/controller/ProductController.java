package com.sb.kc.controller;

import com.sb.kc.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final List<Product> products = new ArrayList<>(List.of(
            new Product(1L, "Laptop", 90000),
            new Product(2L, "Phone", 50000),
            new Product(3L, "Keyboard", 3000)
    ));

    @GetMapping("/products")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Product> getProducts() {
        return products;
    }

    // ADMIN only
    @DeleteMapping("/products/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public String deleteProduct(@PathVariable Long id) {
        products.removeIf(product -> product.id().equals(id));
        return "Product Deleted Successfully";
    }
}