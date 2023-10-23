package com.recipe.finder.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;


@Getter
public class SpringConfig {
    @Value("${spring.API_KEY}")
    private String api_key;
}
