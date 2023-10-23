package com.recipe.finder.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.recipe.finder.domain.ProductDto;
import com.recipe.finder.domain.RecipeDto;
import com.recipe.finder.domain.SingleRecipeDto;
import lombok.Data;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Data
@Service
public class RecipeService {

    private final ProductService productService;
    private final String api_key = System.getenv("API_KEY");
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final ObjectReader arrayReader = mapper.readerForArrayOf(RecipeDto.class);
    private final ObjectReader recipeReader = mapper.readerForArrayOf(SingleRecipeDto.class);

    public RecipeService(ProductService productService) {
        this.productService = productService;
    }

    public HttpRequest createRequestForRecipes(List<ProductDto> allProducts) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(constructUriForRecipes(allProducts))
                .build();
    }

    private URI constructUriForRecipes(List<ProductDto> allProducts) throws URISyntaxException {
        List<String> productNames = allProducts.stream()
                .map(ProductDto::getName)
                .toList();
        return new URIBuilder("https://api.spoonacular.com")
                .setPath("/recipes/findByIngredients")
                .addParameter("apiKey", api_key)
                .addParameter("ingredients", productNames.toString())
                .build();
    }

    public RecipeDto[] getRecipesForAllProducts() throws Exception {
        List<ProductDto> allProducts = productService.getProducts();
        HttpRequest request = createRequestForRecipes(allProducts);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        RecipeDto[] recipesArrayNode = arrayReader.readValue(body, RecipeDto[].class);
        return recipesArrayNode;
    }

    public HttpRequest createRequestForSingleRecipe(String recipeID) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(constructUriForSingleRecipe(recipeID))
                .build();
    }

    private URI constructUriForSingleRecipe(String recipeID) throws URISyntaxException {
        return new URIBuilder("https://api.spoonacular.com/recipes/")
                .addParameter("apiKey", api_key)
                .setPath("/recipes/" + recipeID + "/information")
                .build();
    }

    public SingleRecipeDto getRecipe(String recipeID) throws Exception {
        HttpRequest request = createRequestForSingleRecipe(recipeID);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        SingleRecipeDto singleRecipe = recipeReader.readValue(body, SingleRecipeDto.class);
        return singleRecipe;
    }


}
