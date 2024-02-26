package Model;

import Observers.ElementChangeObserver;

public interface WorldElement {
    boolean isAnimal();

    Vector2d getPosition();

    Directions getOrientation();

    int getImageIdx();

    void setObserver(ElementChangeObserver observer);

    int getActiveGenome();
}
