package com.example.springdatajpahibernatemapping.Controller;

import com.example.springdatajpahibernatemapping.Exception.ResourceNotFoundException;
import com.example.springdatajpahibernatemapping.Model.Product;
import com.example.springdatajpahibernatemapping.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/api")
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product t = productsRepository.save(new Product(product.getName(), product.getDescription(), product.getImagepath(), product.getPrice(), product.getQuantity()));
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(@RequestParam(required = false) String name) {
        List<Product> productList = new ArrayList<>();
        if (name == null) {
            productsRepository.findAll().forEach(productList::add);
        } else {
            productsRepository.findByName(name).forEach(productList::add);
        }
        if (productList.isEmpty()) {
            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductsById(@PathVariable("id") long id) throws ResourceNotFoundException {
        Product product = productsRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("No Product found with this id: "+ id));

        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String name) {
        List<Product> products = productsRepository.findByNameContainingIgnoreCase(name);
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product p) throws ResourceNotFoundException {
        Product product = productsRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Product Id " + id + "NotFound!"));

        product.setDescription((p.getDescription()));
        product.setName(p.getName());

        return new ResponseEntity<>(productsRepository.save(product), HttpStatus.OK);
    }

    @DeleteMapping("products")          // This method will delete all products
    public ResponseEntity<Product> deleteAllProduct() {
        productsRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/products/{id}") // Delete a product by ID
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        if (productsRepository.existsById(id)) {
            productsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
