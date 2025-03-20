package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import janggi.view.PieceType;

public abstract class Piece {

    private final PieceType pieceType;
    private final Team team;

    public Piece(PieceType pieceType, Team team) {
        this.pieceType = pieceType;
        this.team = team;
    }

    abstract public Path move(Position currentPosition, Position arrivalPosition);

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }
}
