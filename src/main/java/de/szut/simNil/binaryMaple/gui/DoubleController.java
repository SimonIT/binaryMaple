package de.szut.simNil.binaryMaple.gui;

import java.util.Random;

public class DoubleController extends AbstractController<Double> {

    private static Random r = new Random();

    @Override
    Double getInput(String input) {
        return Double.parseDouble(input.replace(",", "."));
    }

    @Override
    Double getRandomValue() {
        int bounds = Math.max(40, (int) Math.pow(this.tree.getNodeCount(), 2));
        return r.nextDouble() * bounds - (bounds / 2);
    }

    @Override
    public String toString() {
        return "Double";
    }
}
