package janggi.piece.pieces;

import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.piece.pieces.moverule.MoveRule;
import janggi.piece.pieces.moverule.VerticalHorizontalRule;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public class Chariot implements Piece {
    private final Team team;
    private final MoveRule moveRule;

    public Chariot(Team team) {
        this.team = team;
        this.moveRule = new VerticalHorizontalRule();
    }

    @Override
    public List<Route> calculateRoutes(Position start) {
        return moveRule.moveAll(start);
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
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
