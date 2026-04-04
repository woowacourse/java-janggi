package domain.piece;

import domain.Position;
import domain.Team;
import domain.strategy.MoveStrategy;

import java.util.List;

public abstract class Piece {

    private final Team team;
    private final MoveStrategy moveStrategy;

    public Piece(Team team, MoveStrategy moveStrategy) {
        this.team = team;
        this.moveStrategy = moveStrategy;
    }

    public Team getTeam() {
        return team;
    }

    public abstract PieceType getPieceType();

    public boolean canMove(Position currentPosition, Position targetPosition, PieceProvider pieceProvider) {
        if (currentPosition.equals(targetPosition)) {
            return false;
        }
        List<Position> candidates = moveStrategy.getMoveCandidates(currentPosition, pieceProvider);

        return candidates.contains(targetPosition);
    }

    public abstract boolean isBridge();

    public abstract boolean isCatchByCannon();

    public abstract boolean isBlank();
}
