package com.majida.mbook.service;


import com.majida.mbook.entity.Category;
import com.majida.mbook.repository.CategoryRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
        * Test the Category Service Implementation: test the service logic
        *
        * @author majida
        */

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    private static final Long CATEGORY_ID = 2L;


    @Mock
    private CategoryRepository repoMock;

    @InjectMocks
    private CategoryService categoryService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Category category;



    @Before
   public void init() {

        category = new Category();
        category.setId(CATEGORY_ID);
        category.setName("scientifique");




    }

    @Test
   public void getAllCategories(){
        // Data preparation
        List<Category> categories = Arrays.asList(category, category, category);
        Mockito.when(repoMock.findAllByOrderByIdAsc()).thenReturn(categories);

        // Method call
        List<Category> categoryList = categoryService.getAllCategories();

        // Verification
        Assert.assertThat(categoryList, Matchers.hasSize(3));
        Mockito.verify(repoMock, Mockito.times(1)).findAllByOrderByIdAsc();
        Mockito.verifyNoMoreInteractions(repoMock);
    }

    @Test
    public void getCategoryAndCategoryExists() {

        // Data preparation
        Mockito.when(repoMock.findById(CATEGORY_ID)).thenReturn(Optional.of(category));

        // Method call
        Optional<Category> category = categoryService.getCategory(CATEGORY_ID);

        // Verification
        Assert.assertNotNull(category);
        Mockito.verify(repoMock, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(repoMock);


    }


    @Test
    public void updateCategoryAndCategoryNotExists() throws Exception {
        // Method call
        categoryService.updateCategory(CATEGORY_ID, category);
        repoMock.save(category);

        // Verification
        Mockito.verify(repoMock, Mockito.times(2)).save(ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(repoMock);
    }

    @Test
    public void deleteCategoryAndCategoryNotExists() {

        // Method call
        categoryService.deleteCategory(CATEGORY_ID);


        // Verification
        Mockito.verify(repoMock, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(repoMock);



    }
}