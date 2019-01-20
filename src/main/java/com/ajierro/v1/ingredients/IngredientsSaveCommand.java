package com.ajierro.v1.ingredients;

import javax.validation.constraints.NotBlank;

public class IngredientsSaveCommand {

    @NotBlank
    private String name;
    @NotBlank
    private String type;

    public IngredientsSaveCommand() {}

    public IngredientsSaveCommand(String name, String type) {
        this.name = name;
        this.type = type;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
