package com.ujjawal.ecommerce.repository;

import com.ujjawal.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository <Product,Long> {
    List<Product> findAllByCategory_Id(int id);

    @Query("SELECT p FROM Product p WHERE p.isBlocked = 2")
    List<Product> findAllActiveProduct();


    @Query("SELECT p FROM Product p WHERE p.isBlocked = 2 AND p.category.id = :category_id")
    List<Product> findAllProductByCategoryId(@Param("category_id") int categoryId);




}


