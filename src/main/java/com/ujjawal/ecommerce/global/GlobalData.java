package com.ujjawal.ecommerce.global;

import com.ujjawal.ecommerce.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<Product> cart;
    static {
        cart =new ArrayList<Product>();
    }
}
