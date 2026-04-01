package domain.piece;


import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class Chariot extends ActivePiece {

    public Chariot(Team team) {
        super(team, PieceType.CHA);
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

    @Override
    public boolean isCannon() {
        return false;
    }
}
