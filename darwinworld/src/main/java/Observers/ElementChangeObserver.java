package Observers;

import Model.Animal;
import Model.Vector2d;
import Model.WorldElement;

public interface ElementChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal object);

    void animalDies();
}
