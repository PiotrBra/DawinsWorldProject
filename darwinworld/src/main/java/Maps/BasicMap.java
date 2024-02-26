package Maps;

import Model.Vector2d;
import Model.WorldElement;

public class BasicMap extends AbstractWorldMap{
    public BasicMap(int width, int height, int reproductionEnergy) {
        super(width, height, reproductionEnergy);

        float midY = (height - 1) / (float) 2;

        preferredPositions.sort((o1, o2) -> Float.compare(Math.abs(o1.getY() - midY), Math.abs(o2.getY() - midY)));
        emptyPreferred = getPreferred();
        emptyNotPreferred = getNotPreferred();
    }

    @Override
    public boolean moveValidator(Vector2d destination){
        return destination.getY() < upperRight.getY() && destination.getY() >= 0;
    }
    @Override
    public void updatePreferredPositions() {
    }
}
