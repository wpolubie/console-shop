package com.example.consoleshop.service;

import com.example.consoleshop.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Profile("plus")
public class PlusService extends StartService {

    private final List<Product> products;

    @Value("${vat}")
    private int vat;

    public PlusService() {
        this.products = super.generate();
    }

    @Override
    public List<Product> generate() {
        return super.generate();
    }

    public BigDecimal convertVat() {
        var vat = this.vat;
        return BigDecimal.valueOf(vat * 0.01 + 1);
    }

    @Override
    public void addProduct(Product product) {
        super.addProduct(product);
    }

    @Override
    public BigDecimal sumThePrice() {
        return products
                .stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(convertVat());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void showAllProducts() {
        products.forEach(System.out::println);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void showSummaryPrice() {
        System.out.println("Summary price " + sumThePrice().setScale(2, RoundingMode.HALF_UP));
    }

}
