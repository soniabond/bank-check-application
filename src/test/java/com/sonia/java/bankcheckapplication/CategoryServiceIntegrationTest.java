package com.sonia.java.bankcheckapplication;

import com.sonia.java.bankcheckapplication.model.bank.category.Category;
import com.sonia.java.bankcheckapplication.model.bank.category.DischargeEntity;
import com.sonia.java.bankcheckapplication.repository.CategoryRepository;
import com.sonia.java.bankcheckapplication.repository.DischargeRepository;
import com.sonia.java.bankcheckapplication.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

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
    public void testAddDischargeMatch(){

        DischargeEntity discharge = new DischargeEntity();
        String dischargeName = "UKLON";
        discharge.setName(dischargeName);
        String categoryName = "taxi";
        Category category = new Category(1, categoryName);

        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));
        when(categoryRepository.save(same(category))).thenReturn(category);
        when(dischargeRepository.save(same(discharge))).thenReturn(discharge);

        Category categoryResponse = categoryService.addDischargeMatch(dischargeName, categoryName);
        assertThat(categoryResponse).isEqualTo(category);
        assertThat(categoryResponse.getDischarges().contains(discharge)).isEqualTo(true);

        verify(categoryRepository).findByName(categoryName);
        verify(categoryRepository).save(same(category));

    }

}
