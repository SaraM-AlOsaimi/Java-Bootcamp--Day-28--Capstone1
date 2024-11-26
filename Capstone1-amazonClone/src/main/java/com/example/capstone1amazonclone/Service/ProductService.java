package com.example.capstone1amazonclone.Service;

import com.example.capstone1amazonclone.Model.Category;
import com.example.capstone1amazonclone.Model.Product;
import com.example.capstone1amazonclone.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    ArrayList<Product> products = new ArrayList<>();


    public ArrayList<Product> getProducts() {
        return products;
    }

    // i should check if the category is exist
    private final CategoryService categoryService;

    public boolean addProduct(Product product) {
        for (Category category : categoryService.getCategories()) {
            if (category.getId().equals(product.getCategoryID())) {
                products.add(product);
                return true;
            }
        }
        return false;
    }

    public boolean updateProduct(String id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }
//------------------------------

}
