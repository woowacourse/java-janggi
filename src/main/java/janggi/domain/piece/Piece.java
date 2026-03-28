package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.MoveStrategy;
import java.util.List;
import java.util.Map;

public abstract class Piece {
    private final Team team;
    private final Name name;
    private final MoveStrategy moveStrategy;

    public Piece(Team team, Name name, MoveStrategy moveStrategy) {
        this.team = team;
        this.name = name;
        this.moveStrategy = moveStrategy;
    }

    public String getPieceName() {
        return name.getName();
    }

    public String getTeamName() {
        return team.name();
    }

    public boolean isSameTeam(Piece piece) {
        return piece.team.equals(this.team);
    }

    public Team getTeam() {
        return team;
    }

    public boolean canMove(Position from, Position to) {
        return moveStrategy.canMove(from, to);
    }

    public List<Position> findPath(Position from, Position to) {
        return moveStrategy.findPath(from, to);
    }

    abstract public boolean determineMovingRule(Map<Position, Piece> positionPieces, Position to);
}
