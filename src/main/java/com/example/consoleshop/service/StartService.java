package com.example.consoleshop.service;

import com.example.consoleshop.model.Product;
import com.example.consoleshop.utils.ProductGenerator;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Profile("start")
public class StartService {

    private final List<Product> products;

    public StartService() {
        this.products = generate();
    }

    public List<Product> generate() {
        var productGenerator = new ProductGenerator();
        return productGenerator.generateProducts();
    }

    public BigDecimal sumThePrice() {
        return products
                .stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void showAllProducts() {
        products.forEach(System.out::println);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void showSummaryPrice() {
        System.out.println("Summary price " + sumThePrice());
    }

}
