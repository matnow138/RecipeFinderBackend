package com.recipe.finder.controller;


import com.recipe.finder.RecipeController;
import com.recipe.finder.domain.ItemDto;
import com.recipe.finder.domain.RecipeDto;
import com.recipe.finder.domain.SingleRecipeDto;
import com.recipe.finder.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(RecipeController.class)
public class RecipeControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Test
    public void getRecipesForAllItems() throws Exception {
        //Given
        RecipeDto recipeDto = new RecipeDto("1","Test title", 5,0, Arrays.asList());
        RecipeDto[] allRecipes = {recipeDto};
        given(recipeService.getRecipesForAllProducts()).willReturn(allRecipes);

        //When & Then
        mockMvc.perform(get("/v1/recipe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
                //.andExpect(jsonPath("$[0].id", is(recipeDto.getId())));


    }

    @Test
    public void getRecipesForSingleRecipe() throws Exception {
        //Given
        List<ItemDto> itemDtos = List.of(new ItemDto("Title1","kg",2.0), new ItemDto("Title2","lbs",3.0));
        SingleRecipeDto singleRecipeDto = new SingleRecipeDto("Test title",itemDtos, "instruction"  );;
        given(recipeService.getRecipe(anyString())).willReturn(singleRecipeDto);

        //When & Then
        mockMvc.perform(get("/v1/recipe/getRecipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("recipeId","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test title")));
        //.andExpect(jsonPath("$[0].id", is(recipeDto.getId())));


    }
}
