package janggi.piece.pieces;

import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.piece.pieces.moverule.MoveRule;
import janggi.piece.pieces.moverule.StraightDiagonalRule;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public class Elephant implements Piece {
    private static final int MAX_DEPTH = 3;

    private final Team team;
    private final MoveRule moveRule;

    public Elephant(Team team) {
        this.team = team;
        this.moveRule = new StraightDiagonalRule(MAX_DEPTH);
    }

    @Override
    public boolean isCannon() {
        return Piece.super.isCannon();
    }

    @Override
    public List<Route> calculateRoutes(Position start) {
        return moveRule.moveAll(start);
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }

    @Override
    public double getScore() {
        return getType().getScore();
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
