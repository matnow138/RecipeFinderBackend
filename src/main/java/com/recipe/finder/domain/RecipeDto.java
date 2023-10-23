package com.recipe.finder.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDto {

    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("usedIngredientCount")
    private int usedIngredientCount;
    @JsonProperty("missedIngredientCount")
    private int missedIngredientCount;
    @JsonProperty("missedIngredients")
    private List<ItemDto> missedIngredients;

}
