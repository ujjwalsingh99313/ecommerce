package com.ujjawal.ecommerce.controller;

import com.ujjawal.ecommerce.dto.ProductDTO;
import com.ujjawal.ecommerce.model.Category;
import com.ujjawal.ecommerce.model.Product;
import com.ujjawal.ecommerce.service.CategoryService;
import com.ujjawal.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller

public class AdminController {
    public static String uploadDir =System.getProperty("user.dir") +"/src/main/resources/static/productImages";
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;
    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome" ;
    }

    @GetMapping("/admin/categories")
    public String getCat(Model model){
        /*for returning list from categories,html*/
        model.addAttribute("categories",categoryService.getAllCategory());
        return "categories" ;
    }
    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping ("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }
/* this is for delete aur remove the category */
    @GetMapping ("/admin/categories/delete/{id}")
    public String  delCat(@PathVariable int id) {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories" ;
    }

    /*this is for update the category*/
    @GetMapping ("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id , Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()){
                model.addAttribute("category" , category.get());
                return "categoriesAdd";
        }else
            return "404" ;


    }

///////////THIS IS FOR PRODUCT SECTION//////////////////////////////////////

    @GetMapping ("/admin/products")
    public String products(Model model){
        model.addAttribute("productDTO", productService.getAllProduct());
        return "products";
    }
    @GetMapping ("/admin/products/add")
    public String productAddGet(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String productAddPost(@ModelAttribute("productDto")ProductDTO productDTO,
                                 @RequestParam("productImage")MultipartFile file,
                                 @RequestParam("imgName")String imgName) throws IOException {
        Product product =new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        String imageUUID;
        if (!file.isEmpty()){
            imageUUID= file.getOriginalFilename();
            /*for creating file path and also name*/
            Path fileNamePath = Paths.get(uploadDir,imageUUID);
            /* for path name and actual file where its came from*/
            Files.write(fileNamePath,file.getBytes());
        }
        else {
            imageUUID =imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);
        return "redirect:/admin/products";

    }
    @GetMapping ("/admin/product/delete/{id}")
    public String  delPro(@PathVariable long id) {
        productService.removeProductById(id);
        return "redirect:/admin/products" ;
    }


    @GetMapping ("/admin/product/update/{id}")
    public String updateProductGet(@PathVariable long id,Model model){
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO= new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId((product.getCategory().getId()));
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight((product.getWeight()));
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("productDTO",productDTO);

        return "productsAdd";
    }

    @GetMapping ("/admin/product/block/{id}")
    public String  block(@PathVariable long id) {
        productService.blockProductById(id);
        return "redirect:/admin/products" ;
    }

    @GetMapping ("/admin/product/unblock/{id}")
    public String  unblock(@PathVariable long id) {
        productService.unblockProductById(id);
        return "redirect:/admin/products" ;
    }

}
