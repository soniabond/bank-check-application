package com.sonia.java.bankcheckapplication.service;

import com.sonia.java.bankcheckapplication.exceptions.CategoryExceptions;
import com.sonia.java.bankcheckapplication.model.bank.category.Category;
import com.sonia.java.bankcheckapplication.model.bank.category.DischargeEntity;
import com.sonia.java.bankcheckapplication.model.bank.req.CategoryDischargeRequest;
import com.sonia.java.bankcheckapplication.repository.CategoryRepository;
import com.sonia.java.bankcheckapplication.repository.DischargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {


    CategoryRepository categoryRepository;

    DischargeRepository dischargeRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, DischargeRepository dischargeRepository) {
        this.categoryRepository = categoryRepository;
        this.dischargeRepository = dischargeRepository;
    }

    public CategoryService() {
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Set<String> getCategoryResponse() {
        return getAllCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Optional<Category> getCategoryByName(String name){
        return categoryRepository.findByName(name);
    }


    @Transactional
    public List<Category> addCategoryDischarges(List<CategoryDischargeRequest> categoryDischargeRequests){
        List<Category> categories = new ArrayList<>();
        Category category = null;
        for(CategoryDischargeRequest categoryDischargeRequest: categoryDischargeRequests){

            if (categoryDischargeRequest.getDischarge() == null){
                category = addCategory(categoryDischargeRequest.getCategory());
                categories.add(category);
                System.out.println(category);
            }
            else if (categoryDischargeRequest.getDischarge().isEmpty()){
                category = addCategory(categoryDischargeRequest.getCategory());
                categories.add(category);
                System.out.println(category);
            }
            else {
                for (String discharge: categoryDischargeRequest.getDischarge()) {
                    category = addDischargeMatch(categoryDischargeRequest.getCategory(), discharge);
                    categories.add(category);
                    System.out.println(category);
                }
            }
        }
        return categories;
    }

   private Category addCategory(String categoryName){
        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    var newCategory = new Category();
                    newCategory.setName(categoryName);
                    return newCategory;
                });
       System.out.println(categoryName);
        return categoryRepository.save(category);
    }

   private Category addDischargeMatch (String categoryName, String dischargeName){
        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    var newCategory = new Category();
                    newCategory.setName(categoryName);
                    return newCategory;
                });
       System.out.println(category);
        DischargeEntity discharge = new DischargeEntity();
        discharge.setName(dischargeName);
        if (category.getDischarges().contains(discharge)){
            return category;
        }
        discharge.setCategory(category);
        category = categoryRepository.save(category);
        dischargeRepository.save(discharge);
        category.getDischarges().add(discharge);
        System.out.println(category);
        return category;
    }

    @Transactional
    public Optional<Category> deleteCategoryByName(String name){
        Optional<Category> category = categoryRepository.findByName(name);
        category.ifPresent(categoryRepository::delete);
        return category;
    }





}
