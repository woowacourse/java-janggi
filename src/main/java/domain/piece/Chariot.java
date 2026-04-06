package domain.piece;


import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team, PieceType.CHA);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return source.isSameColumn(target) || source.isSameRow(target);
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        if (source.isSameColumn(target)) {
            return source.makeRowStraightRoute(target);
        }

        return source.makeColumnStraightRoute(target);
    }

}
