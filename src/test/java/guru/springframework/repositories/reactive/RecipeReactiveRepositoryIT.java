package guru.springframework.repositories.reactive;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryIT {

    @Autowired
    private RecipeReactiveRepository recipeReactiveRepository;

    @Test
    public void testSaveDocument(){
        Recipe recipe = new Recipe();
        recipe.setDescription("Some description");
        recipe.setCookTime(1);
        recipe.setDifficulty(Difficulty.EASY);

        Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

        List<Recipe> allRecipes = recipeReactiveRepository.findAll().collectList().block();

        Long countAllRecipes = recipeReactiveRepository.count().block();

        assertThat(savedRecipe).isNotNull();
        assertThat(savedRecipe.getId()).isNotNull();

        assertThat(allRecipes).isNotNull();
        assertThat(allRecipes.size()).isEqualTo(1);

        assertThat(allRecipes.get(0).getId()).isEqualTo(savedRecipe.getId());

        assertThat(countAllRecipes).isEqualTo(1);
    }
}
