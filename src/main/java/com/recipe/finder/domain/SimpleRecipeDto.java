package com.recipe.finder.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleRecipeDto {


    private String id;
    private String name;
    private int usedIngredientCount;
    private int missedIngredientCount;
    private List<String> missedIngredients;


    public SimpleRecipeDto(SimpleRecipeDto simpleRecipeDto) {
        this(simpleRecipeDto.id, simpleRecipeDto.name, simpleRecipeDto.usedIngredientCount, simpleRecipeDto.usedIngredientCount, simpleRecipeDto.missedIngredients);
    }
}
