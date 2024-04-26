package com.ujjawal.ecommerce.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    /*for coloums join in jpa we use ManyToOne because for one category their is many products*/
    /*mapping of jpa*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id" , referencedColumnName = "category_id")
    private Category category;
    private double price ;
    private double weight ;
    private String description;
    private String imageName;

    private int isBlocked = 2;

}