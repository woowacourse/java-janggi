package domain.piece;

import domain.piece.strategy.MoveStrategy;
import java.util.Objects;

public abstract class Piece {
    private final MoveStrategy moveStrategy;
    private final PieceType pieceType;
    private final Team team;

    public Piece(MoveStrategy moveStrategy, PieceType pieceType, Team team) {
        this.moveStrategy = moveStrategy;
        this.pieceType = pieceType;
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return pieceType == piece.pieceType && team == piece.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, team);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }
}
