package com.recipe.finder.controller;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
public class RecipeControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getRecipesForAllItems() throws Exception {

    }
}
