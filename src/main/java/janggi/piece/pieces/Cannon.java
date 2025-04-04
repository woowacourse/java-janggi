package janggi.piece.pieces;

import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.piece.pieces.moverule.MoveRule;
import janggi.piece.pieces.moverule.VerticalHorizontalRule;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public class Cannon implements Piece {
    private final Team team;
    private final MoveRule moveRule;

    public Cannon(Team team) {
        this.team = team;
        this.moveRule = new VerticalHorizontalRule();
    }

    @Override
    public List<Route> calculateRoutes(Position start) {
        return moveRule.moveAll(start);
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public double getScore() {
        return getType().getScore();
    }
}
