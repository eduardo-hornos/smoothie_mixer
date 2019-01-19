package com.ajierro.v1.types;

import javax.validation.constraints.NotBlank;

public class TypesSaveCommand {

    @NotBlank
    private String name;

    public TypesSaveCommand() {}

    public TypesSaveCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
