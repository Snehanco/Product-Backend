package com.example.springdatajpahibernatemapping.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="imagepath")
    private String imagepath;

    @Column(name="Price")
    private int price;

    @Column(name="Quantity")
    private int quantity;

    public String toString()
    {

        return "Tutorial[Id="+id+",Tutorial name="+name+",Tutorial description="+description+",Tutorial imagePath="+imagepath+",Tutorial price="+price+",Tutorial quantity="+quantity+"]";
    }

    public Product(String name, String description, String imagepath, int price, int quantity)
    {
        this.name=name;
        this.description=description;
        this.imagepath=imagepath;
        this.price=price;
        this.quantity=quantity;
    }

}
