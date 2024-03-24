package com.example.springdatajpahibernatemapping.repository;

import com.example.springdatajpahibernatemapping.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {

    List<Product> findByPrice(int price);

    List<Product> findByName(String name);

    List<Product> findByNameContainingIgnoreCase(String name);

}
