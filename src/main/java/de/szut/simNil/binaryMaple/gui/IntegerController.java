package de.szut.simNil.binaryMaple.gui;

import org.apache.commons.lang3.RandomUtils;

public class IntegerController extends AbstractController<Integer> {

    @Override
    Integer getInput(String input) {
        return Integer.parseInt(input);
    }

    @Override
    Integer getRandomValue() {
        int bounds = Math.max(40, (int) Math.pow(this.tree.getNodeCount(), 2));
        return RandomUtils.nextInt(0, bounds) - (bounds / 2);
    }

    @Override
    public String toString() {
        return "Integer";
    }
}
