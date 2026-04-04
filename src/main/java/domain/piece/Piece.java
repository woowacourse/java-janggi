package domain.piece;

import domain.PieceProvider;
import domain.position.Position;
import domain.Team;
import domain.strategy.Strategy;
import java.util.List;

public abstract class Piece {
    private final Team team;
    protected final Strategy moveStrategy;

    public Piece(Team team, final Strategy moveStrategy) {
        this.team = team;
        this.moveStrategy = moveStrategy;
    }

    public Team getTeam() {
        return team;
    }

    public abstract boolean canMove(Position from, Position to, Team team, PieceProvider pieceProvider);

    public List<Position> getMoveCandidates(Position from, PieceProvider board) {
        return moveStrategy.getMoveCandidates(from, board);
    }
}
