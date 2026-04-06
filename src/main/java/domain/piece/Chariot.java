package domain.piece;

import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class Chariot extends Piece {
    private static final double SCORE = 13.0;

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public double score() {
        return SCORE;
    }

    @Override
    public boolean canMove(Position source, Position target) {
        if (source.equals(target)) {
            return false;
        }
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
