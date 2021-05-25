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
@Profile("pro")
public class ProService extends StartService {

    @Value("${vat}")
    private int vat;

    @Value("${discount}")
    private int discount;

    private final List<Product> products;


    public ProService() {
        this.products = super.generate();
    }

    public BigDecimal convertVat() {
        var vat = this.vat;
        return BigDecimal.valueOf(vat * 0.01 + 1);
    }

    public BigDecimal convertDiscount() {
        var discount = this.discount;
        return BigDecimal.valueOf(1 - (discount * 0.01));
    }

    @Override
    public List<Product> generate() {
        return super.generate();
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
                .multiply(convertVat())
                .multiply(convertDiscount());
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
