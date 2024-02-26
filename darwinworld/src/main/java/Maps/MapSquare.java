package Maps;

import Model.Animal;
import Model.WorldElement;

import java.util.ArrayList;
import java.util.List;

public class MapSquare {
    private final ArrayList<Animal> objects;
    private boolean grass;
    private int deathCounter;
    private boolean tunnel;

    public MapSquare() {
        this.grass = false;
        this.tunnel = false;
        this.objects = new ArrayList<>();
        this.deathCounter = 0;
    }
    public boolean didGrassGrow() {
        return grass;
    }
    public void growGrass() {
        grass = true;
    }
    public void placeTunnel() {tunnel = true;}
    public boolean isTunnel(){return tunnel;}
    public void eatGrass() {
        grass = false;
    }
    public Animal strongest(){
        return objects.get(0);
    }
    public Animal secondStrongest(){
        return objects.get(1);
    }
    private void increaseDeathCounter() {
        deathCounter += 1;
    }

    public int getDeathCounter() {
        return deathCounter;
    }

    public List<Animal> getObjects() {
        return objects;
    }

    public void removeObject(WorldElement object) {
        objects.remove(object);
    }

    public void placeObject(Animal object) {
        objects.add(object);
        objects.sort(Animal::compareTo);
    }

    public void animalDie(WorldElement animal) {
        removeObject(animal);
        increaseDeathCounter();
    }
    @Override
    public String toString(){
        return objects.toString();
    }
}
