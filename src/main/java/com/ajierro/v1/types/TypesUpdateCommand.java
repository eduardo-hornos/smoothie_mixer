package com.ajierro.v1.types;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TypesUpdateCommand {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    public TypesUpdateCommand() {}

    public TypesUpdateCommand(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
