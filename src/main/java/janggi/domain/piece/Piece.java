package janggi.domain.piece;

import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Score;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;

public abstract class Piece {

    protected final Team team;
    protected final PieceType pieceType;
    protected final MoveRule moveRule;

    public Piece(final Team team, final PieceType pieceType, final MoveRule moveRule) {
        this.team = team;
        this.pieceType = pieceType;
        this.moveRule = moveRule;
    }

    public abstract void checkCanMove(Placement placement, Position departure, Position destination);

    protected void validateMove(final Placement placement, final Position departure, final Position destination) {
        moveRule.validateMoveIsAllowed(departure, destination);
        moveRule.validateRouteIsNotBlocked(placement, Route.of(departure, destination));
    }

    public boolean isAlly(final Team team) {
        return this.team == team;
    }

    public boolean isEnemy(final Team team) {
        return !isAlly(team);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isSameType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public Score getScore() {
        return pieceType.getScore();
    }
}
