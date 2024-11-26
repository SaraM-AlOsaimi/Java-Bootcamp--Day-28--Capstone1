package com.example.capstone1amazonclone.Service;

import com.example.capstone1amazonclone.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    ArrayList<Category> categories = new ArrayList<>();

    //gets all categories
    public ArrayList<Category> getCategories(){
        return categories;
    }

    public void addCategory(Category category){
        categories.add(category);
    }

    public boolean updateCategory(String id , Category category){
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getId().equals(id)){
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id){
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getId().equals(id)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }



}
