package Maps;


import Model.*;
import Observers.ElementChangeObserver;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap, ElementChangeObserver, MoveValidator {
    protected final Map<Vector2d, MapSquare> elements;
    private int animalsNumber;
    private int grassNumber;
    private int animalsDead = 0;
    private int lifeOfDeadAnimal = 0;
    protected final int mapSize;
    private final Vector2d lowerLeft;
    protected final Vector2d upperRight;
    private final int reproductionEnergy;
    protected final List<Vector2d> preferredPositions = new ArrayList<>();
    protected List<Vector2d> emptyPreferred;
    protected List<Vector2d> emptyNotPreferred;
    protected final ArrayList<Animal> animalsList = new ArrayList<>();

    protected AbstractWorldMap(int width, int height, int reproductionEnergy) {
        elements = new HashMap<>();
        this.reproductionEnergy = reproductionEnergy;
        mapSize = width * height;
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width, height);
        animalsNumber = 0;
        grassNumber = 0;

        initMap(width, height);

    }
    @Override
    public abstract boolean moveValidator(Vector2d destination);

    private void initMap(int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vector2d position = new Vector2d(i, j);
                MapSquare square = new MapSquare();
                elements.put(position, square);
                preferredPositions.add(position);
            }
        }
    }
    protected Vector2d calcPosition(Vector2d position){
        return new Vector2d(position.getX() >= 0 ? position.getX() % upperRight.getX() : upperRight.getX() + (position.getX() % upperRight.getX()), position.getY());
    }

    @Override
    public void moveAnimals(){
        for (Animal animal : animalsList){
            MoveTuple posChange = animal.move(this::moveValidator);
            Vector2d newCords = calcPosition(posChange.newPosition);
            Vector2d oldCords = calcPosition(posChange.oldPosition);
            posChange.setNewPosition(newCords);
            posChange.setOldPosition(oldCords);
            positionChanged(posChange.oldPosition,posChange.newPosition,animal);
            animal.subEnergy(3);
        }
    }
    public void eatGrassAnimals(){
        for (Animal animal : animalsList){
            eatGrass(animal);
        }
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal object) {
        if (elements.containsKey(oldPosition) && elements.containsKey(newPosition)) {
            elements.get(oldPosition).removeObject(object);
            elements.get(newPosition).placeObject(object);
        }
    }
    @Override
    public void animalDies(){
        List<Animal> toRemove = new ArrayList<>();
        for (Animal animal : animalsList){
            if (animal.getEnergy() <= 0) {
                toRemove.add(animal);
            }
        }
        for (Animal animal : toRemove){
            animalsList.remove(animal);
            elements.get(calcPosition(animal.getPosition())).getObjects().remove(animal);
            animalsNumber -= 1;
            animalsDead += 1;
        }
    }
    public abstract void updatePreferredPositions();

    public List<Vector2d> getPreferred() {
        return new LinkedList<>(preferredPositions.subList(0, (int) Math.round(0.2 * mapSize)));
    }

    protected List<Vector2d> getNotPreferred() {
        return new LinkedList<>(preferredPositions.subList((int) Math.round(0.2 * mapSize), preferredPositions.size()));
    }

    protected boolean isEmptySquares() {
        return !emptyPreferred.isEmpty() || !emptyNotPreferred.isEmpty();
    }

    protected Vector2d drawPosition() {
        Random random = new Random();
        Vector2d position;

        if (emptyPreferred.isEmpty()) {
            position = emptyNotPreferred.get(random.nextInt(emptyNotPreferred.size()));
            emptyNotPreferred.remove(position);
            return position;
        }
        if (emptyNotPreferred.isEmpty()) {
            position = emptyPreferred.get(random.nextInt(emptyPreferred.size()));
            emptyPreferred.remove(position);
            return position;
        }

        int preference = random.nextInt(100);
        if (preference >= 20) {
            position = emptyPreferred.get(random.nextInt(emptyPreferred.size()));
            emptyPreferred.remove(position);
        } else {
            position = emptyNotPreferred.get(random.nextInt(emptyNotPreferred.size()));
            emptyNotPreferred.remove(position);
        }
        return position;
    }
    public boolean inMap(Vector2d position) {
        return position.precedes(upperRight) && position.follows(lowerLeft);
    }

    public void animalsReproduce(int day){
        for (int i = 0; i < upperRight.getX(); i++){
            for (int j = 0; j < upperRight.getY(); j++){
                Vector2d position = new Vector2d(i,j);
                if (elements.get(position).getObjects().size() > 1){
                    Animal strongest = elements.get(position).strongest();
                    Animal secondStrongest = elements.get(position).secondStrongest();
                    if (secondStrongest.getEnergy() > secondStrongest.getSettings().getReproductionLostEnergy()) {
                        Animal baby = new Animal(strongest, secondStrongest, day);
                        place(baby);
                        strongest.subEnergy(strongest.getSettings().getReproductionLostEnergy());
                        secondStrongest.subEnergy(secondStrongest.getSettings().getReproductionLostEnergy());
                    }
                }
            }
        }
    }

    @Override
    public void place(Animal object) {
        Vector2d position = calcPosition(object.getPosition());
        if (inMap(position)) {
            elements.get(position).placeObject(object);
            animalsNumber += 1;
            animalsList.add(object);
            object.setObserver(this);
            System.out.println(elements.get(position).toString() + " " + position.toString());
        }
    }

    private void addGrass(Vector2d position) {
        MapSquare square = elements.get(position);
        if (!square.isTunnel()) {
            square.growGrass();
            grassNumber += 1;
        }
    }


    public int getAnimalsNumber() {
        return animalsNumber;
    }

    public int getGrassNumber() {
        return grassNumber;
    }

    private void deleteGrass(Vector2d position) {
        elements.get(position).eatGrass();
        grassNumber -= 1;

        if (getPreferred().contains(position)) {
            emptyPreferred.add(position);
        } else {
            emptyNotPreferred.add(position);
        }
    }

    private boolean isGrass(Vector2d position) {
        return elements.get(position).didGrassGrow();
    }

    public void eatGrass(Animal animal) {
        Vector2d position = calcPosition(animal.getPosition());
        if (elements.get(position).didGrassGrow()) {
            animal.addEnergy();
            deleteGrass(calcPosition(position));
        }
    }

    public void growGrass(int grassPerDay) {
        for (int i = 0; i < grassPerDay; i++) {
            if (isEmptySquares()) {
                Vector2d position = drawPosition();
                addGrass(position);
            }
        }
    }

    public Map<Vector2d, MapSquare> getElements() {
        return elements;
    }

    public String toString() {
        return new MapVisualizer(this).draw(lowerLeft, upperRight);
    }

    public int getAnimalsDead() {
        return animalsDead;
    }

    private void setAnimalsDead() {
        this.animalsDead = this.animalsDead + 1;
    }

    public int getLifeOfDeadAnimal() {
        return lifeOfDeadAnimal;
    }

    public void setLifeOfDeadAnimal(Animal animal) {
        //this.lifeOfDeadAnimal = this.lifeOfDeadAnimal + animal.getLifeLength();
    }
    public boolean isMapDead(){
        return animalsList.size() == 0 ? true : false;
    }
}
