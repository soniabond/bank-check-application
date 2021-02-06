package com.sonia.java.bankcheckapplication;

import com.sonia.java.bankcheckapplication.model.bank.category.Category;
import com.sonia.java.bankcheckapplication.model.bank.category.DischargeEntity;
import com.sonia.java.bankcheckapplication.model.bank.req.CategoryDischargeRequest;
import com.sonia.java.bankcheckapplication.repository.CategoryRepository;
import com.sonia.java.bankcheckapplication.repository.DischargeRepository;
import com.sonia.java.bankcheckapplication.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CategoryServiceIntegrationTest {


    CategoryService categoryService;

    CategoryRepository categoryRepository;

    DischargeRepository dischargeRepository;

    @Before
    public void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        dischargeRepository = mock(DischargeRepository.class);
        categoryService = new CategoryService(categoryRepository,
                dischargeRepository);
    }

    @Test
    public void getCategoryByName(){
        String absentName = "abc";
        String presentName = "taxi";
        Category category = new Category(1, presentName);

        when(categoryRepository.findByName(absentName)).thenReturn(Optional.empty());
        when(categoryRepository.findByName(presentName)).thenReturn(Optional.of(category));

        Optional<Category> absentResponse = categoryService.getCategoryByName(absentName);

        assertThat(absentResponse).isEmpty();
        verify(categoryRepository).findByName(absentName);

        Optional<Category> presentResponse = categoryService.getCategoryByName(presentName);

        assertThat(presentResponse).hasValueSatisfying(category1 ->category1.equals(presentResponse));
        verify(categoryRepository).findByName(presentName);
        verify(categoryRepository).findByName(presentName);

        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void testDeleteCategory(){
        String absentName = "abc";
        String presentName = "taxi";
        Category category = new Category(1, presentName);

        when(categoryRepository.findByName(absentName)).thenReturn(Optional.empty());
        when(categoryRepository.findByName(presentName)).thenReturn(Optional.of(category));

        Optional<Category> absentResponse = categoryService.deleteCategoryByName(absentName);
        assertThat(absentResponse).isEmpty();
        verify(categoryRepository).findByName(absentName);

        Optional<Category> presentResponse = categoryService.deleteCategoryByName(presentName);
        assertThat(presentResponse).hasValueSatisfying(category1 ->category1.equals(presentResponse));
        verify(categoryRepository).findByName(presentName);
        verify(categoryRepository).delete(category);

        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void testCategoryDischarge(){

        List<CategoryDischargeRequest>categoryDischargeRequests =
                new ArrayList<>();
        String emptyCategoryName = "taxi";
        CategoryDischargeRequest emptyCategoryRequest = new CategoryDischargeRequest(emptyCategoryName);
        Category emptyCategory = new Category(1, "taxi");


        String fullCategoryName = "food";
        List<String> discharges = new ArrayList<>();
        DischargeEntity first = new DischargeEntity(1L, "Klass");

        DischargeEntity second = new DischargeEntity(2L, "Silpo");

        discharges.add("Klass");
        discharges.add("Silpo");


        CategoryDischargeRequest fullCategoryRequest = new CategoryDischargeRequest(fullCategoryName, discharges);
        Category fullCategory = new Category(2, fullCategoryName);

        categoryDischargeRequests.add(fullCategoryRequest);
        categoryDischargeRequests.add(emptyCategoryRequest);


        when(categoryRepository.findByName(emptyCategoryName)).thenReturn(Optional.of(emptyCategory));
        when(categoryRepository.findByName(fullCategoryName)).thenReturn(Optional.of(fullCategory));
        when(categoryRepository.save(same(emptyCategory))).thenReturn(emptyCategory);
        when(categoryRepository.save(same(fullCategory))).thenReturn(fullCategory);
        when(dischargeRepository.save(same(first))).thenReturn(first);
        when(dischargeRepository.save(same(second))).thenReturn(second);

        List <Category> categoriesResponse = categoryService.addCategoryDischarges(categoryDischargeRequests);
        assertThat(categoriesResponse.size()).isEqualTo(3);
        Category category = categoriesResponse.get(1);
        assertThat(category.getDischarges().size()).isEqualTo(2);
        assertThat(category.getDischarges().contains(first)).isEqualTo(true);
        assertThat(category.getDischarges().contains(second)).isEqualTo(true);
        assertThat(category.getName()).isEqualTo(fullCategoryName);
        Category emptyCategoryResp = categoriesResponse.get(2);
        assertThat(emptyCategoryResp.getName()).isEqualTo(emptyCategoryName);
        assertThat(emptyCategoryResp.getDischarges().size()).isEqualTo(0);

        verify(categoryRepository).findByName(emptyCategoryName);
        verify(categoryRepository, times(2)).findByName(fullCategoryName);
        verify(categoryRepository).save(emptyCategory);
        verify(categoryRepository, times(2)).save(fullCategory);
        verify(dischargeRepository).save(first);
        verify(dischargeRepository).save(second);
        verifyNoMoreInteractions(dischargeRepository);
        verifyNoMoreInteractions(categoryRepository);

    }

}
