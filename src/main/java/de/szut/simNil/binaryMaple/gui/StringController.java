package de.szut.simNil.binaryMaple.gui;

import org.apache.commons.lang3.RandomStringUtils;

public class StringController extends AbstractController<String> {

    @Override
    String getInput(String input) {
        return input;
    }

    /**
     * @return random string with at least one character; the maximum length of the string depends on the tree's height
     */
    @Override
    String getRandomValue() {
        return RandomStringUtils.random(Math.max(5, this.tree.getHeight()));
    }

    @Override
    public String toString() {
        return "String";
    }

    @Override
    public Class<?> getType() {
        return String.class;
    }
}
