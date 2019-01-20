package com.ajierro.v1.ingredients;

import com.ajierro.domain.Ingredients;
import com.ajierro.v1.utils.SortingAndOrderArguments;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;

@Validated
@Controller("/api/v1/ingredients")
public class IngredientsController {

    private final static Logger logger = LoggerFactory.getLogger(IngredientsController.class);

    @Autowired
    private final IngredientsRepository ingredientsRepository;

    public IngredientsController(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    @Get(value = "{?args*}")
    public HashMap<String, Object> list(@Valid SortingAndOrderArguments args) {
        long initTime = System.currentTimeMillis();
        logger.info("[(GET)/api/v1/ingredients] Inbound request");
        HashMap<String, Object> response = new HashMap<>();
        response.put("ingredients",ingredientsRepository.findAll(args));
        logger.info("[(GET)/api/v1/ingredients] Time response: " + (System.currentTimeMillis() - initTime));
        return response;
    }

    @Get("/{id}")
    public Ingredients show(Long id) {
        return ingredientsRepository
                .findById(id)
                .orElse(null);
    }

    @Post("/")
    public HttpResponse<Ingredients> save(@Body @Valid IngredientsSaveCommand cmd) {
        Ingredients ingredient = ingredientsRepository.save(cmd.getName(), cmd.getDescription());

        return HttpResponse
                .created(ingredient)
                .headers(headers -> headers.location(location(ingredient.getId())));
    }

    @Put("/{id}")
    public HttpResponse update(@Body @Valid IngredientsUpdateCommand command, Long id) {
        int numberOfEntitiesUpdated = ingredientsRepository.update(id, command.getName(), command.getDescription());

        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(id).getPath());
    }


    @Delete("/{id}")
    public HttpResponse delete(Long id) {
        ingredientsRepository.deleteById(id);
        return HttpResponse.noContent();
    }

    private URI location(Long id) {
        return URI.create("/ingredients/" + id);
    }

    protected URI location(Ingredients ingredient) {
        return location(ingredient.getId());
    }
}
