package Maps;

import Model.Animal;
import Model.WorldElement;


/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap {

    void place(Animal animal);
    void moveAnimals();
}
