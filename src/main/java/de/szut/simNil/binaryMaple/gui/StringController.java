package de.szut.simNil.binaryMaple.gui;

public class StringController extends AbstractController<String> {
    @Override
    String getInput(String input) {
        return input;
    }

    @Override
    String getRandomValue() {
        return null; // TODO
    }

    @Override
    public String toString() {
        return "String";
    }
}
