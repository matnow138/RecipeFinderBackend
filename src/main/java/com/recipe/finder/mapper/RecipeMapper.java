package com.recipe.finder.mapper;

import com.recipe.finder.domain.RecipeDto;
import com.recipe.finder.domain.SimpleRecipeDto;
import org.springframework.stereotype.Service;

@Service
public class RecipeMapper {

    public SimpleRecipeDto mapToSimpleRecipeDto(RecipeDto recipeDto) {
        return SimpleRecipeDto.builder()
                .id(recipeDto.getId())
                .name(recipeDto.getTitle())
                .usedIngredientCount(recipeDto.getUsedIngredientCount())
                .missedIngredientCount(recipeDto.getMissedIngredientCount())
                .missedIngredients(recipeDto.getMissedIngredients().stream()
                        .map(itemDto -> itemDto.getName())
                        .toList())
                .build();
    }

}
