package com.ajierro.v1.ingredients;

public class IngredientsUpdateCommand {

    private String name;
    private String type;

    public IngredientsUpdateCommand() {}

    public IngredientsUpdateCommand(String name, String type) {
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
