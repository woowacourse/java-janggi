package janggi.model.piece;

import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.Set;

public abstract class Piece {
    private final PieceIdentity pieceIdentity;

    protected Piece(PieceIdentity pieceIdentity) {
        this.pieceIdentity = pieceIdentity;
    }

    public PieceIdentity identity() {
        return pieceIdentity;
    }

    public Color getColor() {
        return pieceIdentity.color();
    }

    public boolean isEqualsColor(Color color) {
        return pieceIdentity.color() == color;
    }

    public abstract Set<Position> calculateMovablePositions(Position start, OccupiedPositions occupied);

    public abstract double getScore();

}
