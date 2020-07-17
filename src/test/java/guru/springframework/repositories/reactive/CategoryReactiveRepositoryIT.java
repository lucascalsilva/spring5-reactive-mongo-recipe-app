package guru.springframework.repositories.reactive;

import guru.springframework.domain.Category;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryIT {

    @Autowired
    private CategoryReactiveRepository categoryReactiveRepository;

    @Test
    public void testSaveDocument(){
        Category category = new Category();
        category.setDescription("American");

        Category savedCategory = categoryReactiveRepository.save(category).block();

        List<Category> allCategories = categoryReactiveRepository.findAll().collectList().block();

        Long countAllCategories = categoryReactiveRepository.count().block();

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isNotNull();

        assertThat(allCategories).isNotNull();
        assertThat(allCategories.size()).isEqualTo(1);

        assertThat(allCategories.get(0).getId()).isEqualTo(savedCategory.getId());

        assertThat(countAllCategories).isEqualTo(1);
    }
}
