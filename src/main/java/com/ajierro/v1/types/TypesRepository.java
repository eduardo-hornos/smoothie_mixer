package com.ajierro.v1.types;

import com.ajierro.domain.Types;
import com.ajierro.v1.utils.SortingAndOrderArguments;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TypesRepository {
    Optional<Types> findById(@NotNull Long id);
    Types save(@NotBlank String name, @NotBlank String description, @NotNull Date created, @NotNull Date updated);
    void deleteById(@NotNull Long id);
    List<Types> findAll(@NotNull SortingAndOrderArguments args);
    int update(@NotNull Long id, @NotBlank String name);
}
