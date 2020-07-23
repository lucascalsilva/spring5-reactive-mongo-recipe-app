package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class ImageServiceImplTest {

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    ImageServiceImpl imageServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        imageServiceImpl = new ImageServiceImpl(recipeReactiveRepository);
    }

    @Test
    public void saveImageFile() throws Exception {
        //given
        String id = "1";
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Framework Guru".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe));

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageServiceImpl.saveImageFile(id, multipartFile).block();

        //then
        verify(recipeReactiveRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }

    @Test
    public void saveIsReactive() throws Exception {
        //given
        String id = "1";
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Framework Guru".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe));

        //when
        Mono<Void> saveImageMono = imageServiceImpl.saveImageFile(id, multipartFile);

        //then
        verify(recipeReactiveRepository, never()).save(any());

        saveImageMono.block();

        verify(recipeReactiveRepository, times(1)).save(any());
    }

    /*@Test
    public void saveNonReactive() throws Exception {
        //given
        String id = "1";
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Framework Guru".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe));

        //when
        Mono<Void> saveImageMono = imageServiceImpl.saveImageFileNonReactive(id, multipartFile);

        //then
        verify(recipeReactiveRepository, times(1)).save(any());
    }*/
}