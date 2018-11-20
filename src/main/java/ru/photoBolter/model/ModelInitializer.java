package ru.photoBolter.model;

public class ModelInitializer {

    public static void init(Model model) {
        new PropertiesLoader().load(model);
    }
}
