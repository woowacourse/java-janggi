package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import java.util.List;

public abstract class Piece {

    protected final String name;
    protected final Side side;

    protected Piece(String name, Side side) {
        this.name = name;
        this.side = side;
    }

    public abstract boolean isEmpty();

    public abstract List<Location> calculateRoute(Location from, Location to);

    public abstract void detectCollision(List<Piece> piecesOnPath);

    public boolean isSameSide(Piece piece) {
        return this.side.equals(piece.side);
    }

    public String getName() {
        return name;
    }
}
