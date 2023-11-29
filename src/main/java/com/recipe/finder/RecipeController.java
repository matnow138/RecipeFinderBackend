package com.recipe.finder;

import com.recipe.finder.domain.RecipeDto;
import com.recipe.finder.domain.SingleRecipeDto;
import com.recipe.finder.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("v1/recipe")
public class RecipeController {

    public final RecipeService recipeService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<RecipeDto[]> getRecipesForAllItems() throws Exception {
        return ResponseEntity.ok().body(recipeService.getRecipesForAllProducts());
    }

    @GetMapping(value = "/getRecipe")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<SingleRecipeDto> getRecipe(@RequestParam String recipeId) throws Exception {
        return ResponseEntity.ok().body(recipeService.getRecipe(recipeId));


    }
}
