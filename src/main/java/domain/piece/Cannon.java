package domain.piece;

import domain.position.Position;
import domain.game.Team;
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
