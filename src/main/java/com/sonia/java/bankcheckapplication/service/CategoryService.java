package com.sonia.java.bankcheckapplication.service;

import com.sonia.java.bankcheckapplication.exceptions.CategoryExceptions;
import com.sonia.java.bankcheckapplication.model.bank.category.Category;
import com.sonia.java.bankcheckapplication.model.bank.category.DischargeEntity;
import com.sonia.java.bankcheckapplication.repository.CategoryRepository;
import com.sonia.java.bankcheckapplication.repository.DischargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public Optional<Category> getCategoryByName(String name){
        return categoryRepository.findByName(name);
    }

    @Transactional
    public Category addDischargeMatch (String dischargeName, String categoryName){
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> CategoryExceptions.categoryNotFound(categoryName));
        DischargeEntity discharge = new DischargeEntity();
        discharge.setName(dischargeName);
        if (category.getDischarges().contains(discharge)){
            return category;
        }
        dischargeRepository.save(discharge);
        category.getDischarges().add(discharge);
        category = categoryRepository.save(category);
        return category;
    }

    @Transactional
    public Optional<Category> deleteCategoryByName(String name){
        Optional<Category> category = categoryRepository.findByName(name);
        category.ifPresent(categoryRepository::delete);
        return category;
    }




}
