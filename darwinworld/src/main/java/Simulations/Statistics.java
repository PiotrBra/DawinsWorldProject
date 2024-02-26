package Simulations;

import Maps.AbstractWorldMap;
import Model.Animal;

import java.util.List;

public class Statistics {
    private final AbstractWorldMap map;
    private int numberAnimals;
    private int numberGrass;
    private int numberDeadAnimals;
    private int worldDays;
    private double avgLife;
    private double avgEnergy;
    private int freePositionQuantity;
    private double avgChildren;
    private int dominantGenotype;
    private final SimulationEngine engine;

    public Statistics(SimulationEngine engine) {
        this.engine = engine;
        map = engine.getSettings().getMap();
        numberAnimals = 0;
        numberGrass = 0;
        numberDeadAnimals = 0;
        worldDays = 0;
        avgLife = 0;
        avgEnergy = 0;
        freePositionQuantity = 0;
        avgChildren = 0;
        dominantGenotype = 0;
        freePositionQuantity = engine.getFreePositionQuantity();
    }

    public void updateStats() {
        AbstractWorldMap map = engine.getSettings().getMap();
        numberAnimals = map.getAnimalsNumber();
        numberGrass = map.getGrassNumber();
        numberDeadAnimals = map.getAnimalsDead();
        worldDays = engine.getCurrentDay();
        freePositionQuantity = engine.getFreePositionQuantity();
    }

    public int getNumberAnimals() {
        return numberAnimals;
    }

    public int getNumberGrass() {
        return numberGrass;
    }

    public int getNumberDeadAnimals() {
        return numberDeadAnimals;
    }

    public int getWorldDays() {
        return worldDays;
    }

    public double getAvgLife() {
        return avgLife;
    }

    public double getAvgEnergy() {
        return avgEnergy;
    }

    public int getFreePositionQuantity() {
        return freePositionQuantity;
    }

    public double getAvgChildren() {
        return avgChildren;
    }

    public int getDominantGenotype() {
        return dominantGenotype;
    }
}

