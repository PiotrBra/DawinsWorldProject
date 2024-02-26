package Maps;

import Model.*;
import java.util.*;

public class TunnelMap extends AbstractWorldMap{
    private final int numberOfTunnels;
    private Map<Vector2d, Tunnel> tunnels = new HashMap<>();

    public TunnelMap(int numberOfTunnels, int width, int height, int reproductionEnergy) {
        super(width, height, reproductionEnergy);
        float midY = (height - 1) / (float) 2;

        preferredPositions.sort((o1, o2) -> Float.compare(Math.abs(o1.getY() - midY), Math.abs(o2.getY() - midY)));
        emptyPreferred = getPreferred();
        emptyNotPreferred = getNotPreferred();
        this.numberOfTunnels = numberOfTunnels;
        List<Vector2d> positions = new LinkedList<>();
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(height,width,numberOfTunnels);
        for(Vector2d tunnelPosition : randomPositionGenerator) {
            positions.add(tunnelPosition);
        }
        for(int i=0; i<positions.size();i+=2){
            Tunnel tunnel = new Tunnel(positions.get(i),positions.get(i+1));
            tunnels.put(tunnel.getEntry1(),tunnel);
            tunnels.put(tunnel.getEntry2(),tunnel);
            addTunnel(positions.get(i),positions.get(i+1));
        }

    }
    @Override
    public void moveAnimals(){
        for (Animal animal : animalsList){
            MoveTuple posChange = animal.move(this::moveValidator);
            Vector2d newCords = calcPosition(posChange.newPosition);
            Vector2d oldCords = calcPosition(posChange.oldPosition);
            posChange.setNewPosition(newCords);
            posChange.setOldPosition(oldCords);
            Tunnel tunnel = tunnels.get(posChange.newPosition);
            if (tunnel != null){
                posChange.setNewPosition(tunnel.otherEntry(newCords));
                animal.setPosition(tunnel.otherEntry(newCords));
            }
            positionChanged(posChange.oldPosition,posChange.newPosition,animal);
            animal.subEnergy(3);
        }
    }
    @Override
    public boolean moveValidator(Vector2d destination){
        return destination.getY() < upperRight.getY() && destination.getY() >= 0;
    }
    @Override
    public void updatePreferredPositions() {

    }
    private void addTunnel(Vector2d position1, Vector2d position2) {
        MapSquare square1 = elements.get(position1);
        MapSquare square2 = elements.get(position2);
        square1.placeTunnel();
        square2.placeTunnel();
    }

}
