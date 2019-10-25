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
        return r.nextDouble();
    }   // TODO: create distribution similar to IntegerController

    @Override
    public String toString() {
        return "Double";
    }
}
