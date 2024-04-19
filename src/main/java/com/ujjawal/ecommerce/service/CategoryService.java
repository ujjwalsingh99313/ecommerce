package com.ujjawal.ecommerce.service;


import com.ujjawal.ecommerce.model.Category;
import com.ujjawal.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;


    /*for retrival*/
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }


    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public void removeCategoryById(int id) { categoryRepository.deleteById(id);}

    /* for fecting */
    public Optional<Category> getCategoryById(int id) {return categoryRepository.findById(id);}

}
