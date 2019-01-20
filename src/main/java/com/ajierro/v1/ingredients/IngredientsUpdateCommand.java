package com.ajierro.v1.ingredients;

public class IngredientsUpdateCommand {

    private String name;
    private String description;

    public IngredientsUpdateCommand() {}

    public IngredientsUpdateCommand(String name, String description) {
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
