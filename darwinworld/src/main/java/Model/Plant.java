package Model;

import Observers.ElementChangeObserver;

public class Plant implements WorldElement{
    private Vector2d position;

    @Override
    public boolean isAnimal() {
        return false;
    }

    @Override
    public Vector2d getPosition() {
        return null;
    }

    @Override
    public Directions getOrientation() {
        return null;
    }

    @Override
    public int getImageIdx() {
        return 0;
    }

    @Override
    public void setObserver(ElementChangeObserver observer) {

    }

    @Override
    public int getActiveGenome() {
        return 0;
    }
}
