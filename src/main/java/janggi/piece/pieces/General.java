package janggi.piece.pieces;

import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.piece.pieces.moverule.MoveRule;
import janggi.piece.pieces.moverule.PalaceRule;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public class General implements Piece {
    private final Team team;
    private final MoveRule moveRule;

    public General(Team team) {
        this.team = team;
        this.moveRule = new PalaceRule();
    }

    @Override
    public List<Route> calculateRoutes(Position position) {
        return moveRule.moveAll(position);
    }

    @Override
    public PieceType getType() {
        return PieceType.GENERAL;
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
