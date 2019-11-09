package de.szut.simNil.binaryMaple.gui;

import org.apache.commons.lang3.RandomStringUtils;

public class StringController extends AbstractController<String> {

    private static String makeMultiline(String inline) {
        StringBuilder builder = new StringBuilder(inline.length());
        int partLength = (int) Math.ceil(Math.sqrt(inline.length()));
        for (int i = 0; i < inline.length(); i += partLength) {
            builder.append(inline, i, Math.min(i + partLength, inline.length()));
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    String getInput(String input) {
        return makeMultiline(input);
    }

    /**
     * @return random string with at least one character; the maximum length of the string depends on the tree's height
     */
    @Override
    String getRandomValue() {
        return makeMultiline(RandomStringUtils.randomAlphanumeric(1, Math.max(5, this.tree.getHeight())));
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
