package Maps;

import Model.Vector2d;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RandomPositionGenerator implements Iterable<Vector2d>, Iterator<Vector2d>{
    private int maxHeight;
    private int maxWidth;
    private int numberOfTunnels;
    private Set<Vector2d> generatedPositions = new HashSet<>();
    private int generatedCount = 0;

    public RandomPositionGenerator(int maxHeight, int maxWidth, int numberOfTunnels) {
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.numberOfTunnels = numberOfTunnels;
    }
    public boolean hasNext() {
        return generatedCount < numberOfTunnels*2;
    }

    public Vector2d next() {
        if (hasNext()) {
            Vector2d randomPosition;
            do {
                randomPosition = new Vector2d(
                        (int) (Math.random() * maxWidth),
                        (int) (Math.random() * maxHeight)
                );
            } while (!generatedPositions.add(randomPosition));

            generatedCount++;
            return randomPosition;
        }
        return null;
    }

    public Iterator<Vector2d> iterator() {
        return this;
    }
}
