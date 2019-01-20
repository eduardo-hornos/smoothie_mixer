package com.ajierro.v1.types;

public class TypesUpdateCommand {

    private String name;
    private String description;

    public TypesUpdateCommand() {}

    public TypesUpdateCommand(String name, String description) {
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
