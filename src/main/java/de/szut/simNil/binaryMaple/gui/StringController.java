package de.szut.simNil.binaryMaple.gui;

import org.apache.commons.lang3.RandomStringUtils;

public class StringController extends AbstractController<String> {

    @Override
    String getInput(String input) {
        return input;
    }

    @Override
    String getRandomValue() {
        return RandomStringUtils.randomAlphanumeric(0, Math.max(5, this.tree.getDepth()));
    }

    @Override
    public String toString() {
        return "String";
    }
}
