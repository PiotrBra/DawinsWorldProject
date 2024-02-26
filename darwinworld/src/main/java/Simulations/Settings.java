package Simulations;

import Maps.*;

public class Settings {
    private final String name;
    private final int mapWidth;
    private final int mapHeight;
    private final int startGrassQuantity;
    private final int eatingGrassEnergy;
    private final int grassPerDay;
    private final int startAnimalsQuantity;
    private final int startAnimalEnergy;
    private final int animalFullEnergy;
    private final int reproductionLostEnergy;
    private final int genLength;
    private final int numberOfTunnels;
    private final int minimalMutationNumber;
    private final int maximalMutationNumber;
    private final AbstractWorldMap map;
    private final int animalMoving;
    //private final IGenome mutationVariant;

    public Settings(String configName, String[] config) throws Exception {
        name = configName;
        mapWidth = Integer.parseInt(config[0]);
        mapHeight = Integer.parseInt(config[1]);
        startGrassQuantity = Integer.parseInt(config[2]);
        eatingGrassEnergy = Integer.parseInt(config[3]);
        grassPerDay = Integer.parseInt(config[4]);
        startAnimalsQuantity = Integer.parseInt(config[5]);
        startAnimalEnergy = Integer.parseInt(config[6]);
        animalFullEnergy = Integer.parseInt(config[7]);
        reproductionLostEnergy = Integer.parseInt(config[8]);
        minimalMutationNumber = Integer.parseInt(config[9]);
        maximalMutationNumber = Integer.parseInt(config[10]);
        genLength = Integer.parseInt(config[11]);
        numberOfTunnels = Integer.parseInt(config[16]);

        if (mapWidth <= 0 || mapHeight <= 0) {
            throw new Exception("wrong map dimension");
        }
        if (startGrassQuantity < 0 || startGrassQuantity > mapWidth * mapHeight) {
            throw new Exception("wrong startGrassQuantity config");
        }
        if (eatingGrassEnergy < 0) {
            throw new Exception("wrong eatingGrassEnergy config");
        }
        if (grassPerDay < 0) {
            throw new Exception("wrong grassPerDay config");
        }
        if (startAnimalsQuantity <= 0 || startAnimalEnergy <= 0) {
            throw new Exception("wrong animal start config");
        }
        if (animalFullEnergy < 0 || reproductionLostEnergy < 0) {
            throw new Exception("wrong reproductionLostEnergy/animalFullEnergy config");
        }
        if (minimalMutationNumber < 0 || minimalMutationNumber > maximalMutationNumber || genLength <= 0) {
            throw new Exception("wrong gen/mutation config");
        }
        if(numberOfTunnels < 0 || numberOfTunnels > (mapWidth*mapHeight -1)/2){
            throw new Exception("wrong number of tunnels config");
        }
        switch (config[13]) {
            case "Predestination" -> animalMoving = 0;
            case "Craziness" -> animalMoving = 1;
            default -> throw new Exception("wrong animalMoving configuration");
        }


        switch (config[15]) {
            case "Earth" -> map = new BasicMap(mapWidth, mapHeight, reproductionLostEnergy);
            case "Tunnel" -> map = new TunnelMap(numberOfTunnels,mapWidth, mapHeight, reproductionLostEnergy);
            default -> throw new Exception("wrong map configuration");
        }
    }

    public String getName() {
        return name;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getStartGrassQuantity() {
        return startGrassQuantity;
    }

    public int getEatingGrassEnergy() {
        return eatingGrassEnergy;
    }

    public int getGrassPerDay() {
        return grassPerDay;
    }

    public int getStartAnimalsQuantity() {
        return startAnimalsQuantity;
    }

    public int getStartAnimalEnergy() {
        return startAnimalEnergy;
    }

    public int getAnimalFullEnergy() {
        return animalFullEnergy;
    }

    public int getReproductionLostEnergy() {
        return reproductionLostEnergy;
    }

    public int getGenLength() {
        return genLength;
    }

    public AbstractWorldMap getMap() {
        return map;
    }
    public int getNumberOfTunnels(){return numberOfTunnels;}

    public int getAnimalMoving() {
        return animalMoving;
    }
    public int getMinimalMutationNumber() {
        return minimalMutationNumber;
    }

    public int getMaximalMutationNumber() {
        return maximalMutationNumber;
    }
}
