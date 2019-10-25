package de.szut.simNil.binaryMaple.gui;

import java.util.Random;

public class IntegerController extends AbstractController<Integer> {

    private static Random r = new Random();

    @Override
    Integer getInput(String input) {
        return Integer.parseInt(input);
    }

    @Override
    Integer getRandomValue() {
        int bounds = Math.max(40, (int) Math.pow(this.tree.getNodeCount(), 2));
        return r.nextInt(bounds) - (bounds / 2);
    }

    @Override
    public String toString() {
        return "Integer";
    }
}
