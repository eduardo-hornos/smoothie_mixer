package com.ajierro.v1.types;

import com.ajierro.domain.Types;
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
@Controller("/api/v1/types")
public class TypesController {

    private final static Logger logger = LoggerFactory.getLogger(TypesController.class);

    @Autowired
    private final TypesRepository typesRepository;

    public TypesController(TypesRepository typesRepository) {
        this.typesRepository = typesRepository;
    }

    @Get(value = "{?args*}")
    public HashMap<String, Object> list(@Valid SortingAndOrderArguments args) {
        long initTime = System.currentTimeMillis();
        logger.info("[(GET)/api/v1/types] Inbound request");
        HashMap<String, Object> response = new HashMap<>();
        response.put("types",typesRepository.findAll(args));
        logger.info("[(GET)/api/v1/types] Time response: " + (System.currentTimeMillis() - initTime));
        return response;
    }

    @Get("/{id}")
    public Types show(Long id) {
        return typesRepository
                .findById(id)
                .orElse(null);
    }

    @Post("/")
    public HttpResponse<Types> save(@Body @Valid TypesSaveCommand cmd) {
        Types type = typesRepository.save(cmd.getName(), cmd.getDescription());

        return HttpResponse
                .created(type)
                .headers(headers -> headers.location(location(type.getId())));
    }

    @Put("/{id}")
    public HttpResponse update(@Body @Valid TypesUpdateCommand command, Long id) {
        int numberOfEntitiesUpdated = typesRepository.update(id, command.getName(), command.getDescription());

        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(id).getPath());
    }

    @Delete("/{id}")
    public HttpResponse delete(Long id) {
        typesRepository.deleteById(id);
        return HttpResponse.noContent();
    }

    private URI location(Long id) {
        return URI.create("/types/" + id);
    }

    protected URI location(Types types) {
        return location(types.getId());
    }
}
