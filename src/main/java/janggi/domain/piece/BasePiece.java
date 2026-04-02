package janggi.domain.piece;

import janggi.domain.status.Team;
import java.util.List;

public abstract class BasePiece implements Piece {

    protected final Team team;
    protected final PieceType type;

    public BasePiece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return route.isEmpty();
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    @Override
    public PieceType getType() {
        return type;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
