package domain.piece;

import domain.game.Team;
import domain.position.Position;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team, PieceDefinition.CHA);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return isStraightMove(source, target);
    }

    private boolean isStraightMove(Position source, Position target) {
        return source.isSameCol(target) || source.isSameRow(target);
    }

    @Override
    public List<Position> searchRoute(Position source, Position target) {
        if (source.isSameCol(target)) {
            return source.betweenSameCol(target);
        }
        return source.betweenSameRow(target);
    }
}
