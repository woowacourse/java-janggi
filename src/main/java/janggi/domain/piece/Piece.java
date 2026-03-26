package janggi.domain.piece;

import janggi.domain.board.Position;
import java.util.List;
import java.util.Map;

public abstract class Piece {
    private final Team team;
    private final Name name;

    public Piece(Team team, Name name) {
        this.team = team;
        this.name = name;
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

    protected boolean isHan() {
        return team == Team.HAN;
    }

    abstract public boolean canMove(Position from, Position to);

    abstract public List<Position> findPath(Position from, Position to);

    abstract public boolean determineMovingRule(Map<Position, Piece> positionPieces, Position to);
}
