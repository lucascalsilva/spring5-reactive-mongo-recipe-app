package guru.springframework.repositories.reactive;

import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryIT {

    @Autowired
    private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Test
    public void testSaveDocument(){
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("Some description");

        UnitOfMeasure savedUnitOfMeasure = unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        List<UnitOfMeasure> allUnitsOfMeasure = unitOfMeasureReactiveRepository.findAll().collectList().block();

        Long countAllUnitOfMeasures = unitOfMeasureReactiveRepository.count().block();

        assertThat(savedUnitOfMeasure).isNotNull();
        assertThat(savedUnitOfMeasure.getId()).isNotNull();

        assertThat(allUnitsOfMeasure).isNotNull();
        assertThat(allUnitsOfMeasure.size()).isEqualTo(1);

        assertThat(allUnitsOfMeasure.get(0).getId()).isEqualTo(savedUnitOfMeasure.getId());

        assertThat(countAllUnitOfMeasures).isEqualTo(1);
    }
}
