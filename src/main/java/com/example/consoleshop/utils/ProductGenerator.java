package com.example.consoleshop.utils;

import com.example.consoleshop.model.Product;
import com.github.javafaker.Faker;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class ProductGenerator {

    private final Faker faker = new Faker();
    public List<Product> generateProducts(){
        return IntStream.range(0,5)
                .mapToObj(index -> generateProduct())
                .collect(Collectors.toList());
    }

    public Product generateProduct(){

        return Product
                .builder()
                .name(faker.food().ingredient())
                .price(BigDecimal.valueOf(faker.number().randomDouble(2, 50L,300L)))
            //    .price(BigDecimal.valueOf(50L))     -  tests
                .build();
    }
}
