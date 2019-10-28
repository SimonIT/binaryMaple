package de.szut.simNil.binaryMaple.gui;

import org.apache.commons.lang3.RandomUtils;

public class DoubleController extends AbstractController<Double> {

    @Override
    Double getInput(String input) {
        return Double.parseDouble(input.replace(",", "."));
    }

    @Override
    Double getRandomValue() {
        int bounds = Math.max(40, (int) Math.pow(this.tree.getNodeCount(), 2));
        return RandomUtils.nextDouble(0, bounds) - (bounds / 2);
    }

    @Override
    public String toString() {
        return "Double";
    }
}
