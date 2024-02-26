package Model;

public enum Directions {
    N,
    NE,
    E,
    SE,
    S,
    SW,
    W,
    NW;
    public Vector2d dirToVector(){
        return switch (this){
            case N -> new Vector2d(0,-1);
            case W -> new Vector2d(-1,0);
            case S -> new Vector2d(0,1);
            case E -> new Vector2d(1,0);
            case NE -> N.dirToVector().add(E.dirToVector());
            case SE -> S.dirToVector().add(E.dirToVector());
            case SW -> S.dirToVector().add(W.dirToVector());
            case NW -> N.dirToVector().add(W.dirToVector());
        };
    }

    public Directions rotation(int steps){
        Directions dir = this;
        for (int i = 0; i < steps; i++){
            dir = dir.next();
        }
        return dir;
    }

    private Directions next() {
        return switch (this) {
            case N -> NE;
            case NE -> E;
            case E -> SE;
            case SE -> S;
            case S -> SW;
            case SW -> W;
            case W -> NW;
            case NW -> N;
        };
    }

    @Override
    public String toString() {
        return switch (this){
            case N -> "NORTH";
            case W -> "WEST";
            case S -> "SOUTH";
            case E -> "EAST";
            case NE -> N.toString() + E.toString();
            case SE -> S.toString() + E.toString();
            case SW -> S.toString() + W.toString();
            case NW -> N.toString() + W.toString();
        };
    }

}
