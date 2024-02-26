package Maps;

import Model.Vector2d;

public class MapVisualizer {
    private static final String EMPTY_CELL = " ";
    private static final String GRASS_CELL = "*";
    private static final String PREFERRED_CELL = "P";
    private static final String CELL_SEGMENT = "|";
    private static final String TUNNEL_CELL = "O";
    private final AbstractWorldMap map;

    public MapVisualizer(AbstractWorldMap map) {
        this.map = map;
    }

    public String draw(Vector2d lowerLeft, Vector2d upperRight) {
        StringBuilder builder = new StringBuilder();

        builder.append(" y\\x ");
        for (int i = lowerLeft.getX(); i < upperRight.getY(); i++) {
            builder.append(String.format("%2d", i));
        }
        builder.append(System.lineSeparator());

        for (int i = lowerLeft.getY(); i < upperRight.getY(); i++) {
            builder.append(String.format("%3d: ", i));
            for (int j = lowerLeft.getX(); j < upperRight.getX(); j++) {
                Vector2d vec = new Vector2d(j, i);
                MapSquare square = map.elements.get(vec);
                if (square == null) {
                    System.out.println("square null");
                } else {
                    builder.append(CELL_SEGMENT);
                    if (!square.getObjects().isEmpty()) {
                        builder.append(square.getObjects().size());
                    } else if (square.didGrassGrow()) {
                        builder.append(GRASS_CELL);
                    } else if (map.getPreferred().contains(vec)) {
                        builder.append(PREFERRED_CELL);
                    }else if (square.isTunnel()){
                        builder.append(TUNNEL_CELL);
                    }
                    else {
                        builder.append(EMPTY_CELL);
                    }
                }
            }
            builder.append(CELL_SEGMENT);
            builder.append(System.lineSeparator());
        }

        //-------------------
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //--------------------

        return builder.toString();
    }
}
