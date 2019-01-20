package com.ajierro.v1.ingredients;

import com.ajierro.domain.Ingredients;
import com.ajierro.v1.utils.SortingAndOrderArguments;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface IngredientsRepository {
    Optional<Ingredients> findById(@NotNull Long id);
    Ingredients save(@NotBlank String name, @NotBlank String description);
    void deleteById(@NotNull Long id);
    List<Ingredients> findAll(@NotNull SortingAndOrderArguments args);
    int update(@NotNull Long id, String name, String description);
}
