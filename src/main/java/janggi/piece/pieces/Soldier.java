package janggi.piece.pieces;

import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.piece.pieces.moverule.MoveRule;
import janggi.piece.pieces.moverule.StraightRule;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public class Soldier implements Piece {
    private final Team team;
    private final MoveRule moveRule;

    public Soldier(Team team) {
        this.team = team;
        this.moveRule = new StraightRule(team);
    }

    @Override
    public List<Route> calculateRoutes(Position start) {
        return moveRule.moveAll(start);
    }

    @Override
    public boolean isCannon() {
        return Piece.super.isCannon();
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public double getScore() {
        return this.getType().getScore();
    }
}
