package janggi.domain.move;

public enum Movement {
    UP(new Vector(-1, 0)),
    DOWN(new Vector(1, 0 )),
    RIGHT(new Vector(0, 1)),
    LEFT(new Vector(0, -1)),
    RIGHT_UP(new Vector(-1, 1)),
    RIGHT_DOWN(new Vector(1, 1)),
    LEFT_UP(new Vector(-1,-1)),
    LEFT_DOWN(new Vector(1, -1)),
    ;

    private final Vector vector;

    Movement(Vector vector) {
        this.vector = vector;
    }

    public Vector getVector() {
        return vector;
    }
}
