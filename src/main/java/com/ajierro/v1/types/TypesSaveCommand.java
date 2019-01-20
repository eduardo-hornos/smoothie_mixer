package com.ajierro.v1.types;

import javax.validation.constraints.NotBlank;

public class TypesSaveCommand {

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public TypesSaveCommand() {}

    public TypesSaveCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
