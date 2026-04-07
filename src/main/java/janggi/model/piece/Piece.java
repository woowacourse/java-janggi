package janggi.model.piece;

import janggi.model.Team;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import java.util.List;

public abstract class Piece {
    protected final Team team;
    private final PieceType pieceType;

    protected Piece(
            Team team,
            PieceType pieceType
    ) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public abstract PositionPath getLegalPath(Position from, Position to);

    public abstract boolean canPassThrough(List<Piece> piecesOnPath, Piece pieceAtTo);

    public abstract boolean canPassThrough(List<Piece> piecesOnPath);

    public boolean isSameTeam(Piece other) {
        return this.team.equals(other.team);
    }

    public boolean isSameTeam(Team other) {
        return this.team.equals(other);
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
