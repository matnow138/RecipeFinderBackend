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
public class SingleRecipeDto {

    @JsonProperty("title")
    private String title;
    @JsonProperty("extendedIngredients")
    private List<ItemDto> extendedIngredients;
    @JsonProperty("instructions")
    private String instructions;
}
