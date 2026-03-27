package domain.activePiece;

import domain.Position;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.List;

public class Cannon extends ActivePiece {

    public Cannon(Team team) {
        super(team, PieceType.PHO);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return source.isSameCol(target) || source.isSameRow(target);
    }

    @Override
    public List<Position> searchRoute(Position source, Position target) {
        if (source.isSameCol(target)) {
            return source.makeRowStraightRoute(target);
        }

        return source.makeColStraightRoute(target);
    }
}
