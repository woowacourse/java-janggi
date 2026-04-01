package domain.piece;

import domain.game.Team;
import domain.position.Position;
import java.util.List;

public class Chariot extends ActivePiece {

    public Chariot(Team team) {
        super(team, PieceDefinition.CHA);
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
