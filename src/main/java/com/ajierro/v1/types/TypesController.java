package com.ajierro.v1.types;

import com.ajierro.domain.Types;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@Controller("/api/v1/types")
public class TypesController {

    private final static Logger logger = LoggerFactory.getLogger(TypesController.class);

    @Autowired
    protected final TypesRepository typesRepository;

    public TypesController(TypesRepository typesRepository) {
        this.typesRepository = typesRepository;
    }

    @Get(value = "/")
    public HashMap<String, Object> list() {
        Long initTime = System.currentTimeMillis();
        logger.info("[(GET)/api/v1/types] Inbound request");
        HashMap<String, Object> response = new HashMap<>();
        response.put("types",typesRepository.findAll(null));
        logger.info("[(GET)/api/v1/types] Time response: " + (System.currentTimeMillis() - initTime));
        return response;
    }

    @Get("/{id}")
    public Types show(Long id) {
        return typesRepository
                .findById(id)
                .orElse(null);
    }
}
