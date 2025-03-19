package domain.piece;

import domain.Board;
import domain.Coordinate;
import domain.Team;
import java.util.Set;

public class Ma extends Piece {

    public Ma(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return Set.of(
                departure.pickChangedCoordinate(-2, -1),
                departure.pickChangedCoordinate(-2, 1),
                departure.pickChangedCoordinate(-1, -2),
                departure.pickChangedCoordinate(-1, 2),
                departure.pickChangedCoordinate(1, -2),
                departure.pickChangedCoordinate(1, 2),
                departure.pickChangedCoordinate(2, -1),
                departure.pickChangedCoordinate(2, 1)
        );
    }

    @Override
    protected boolean canMoveConsideringObstacles(Board board, Coordinate departure, Coordinate arrival) {
        for (Coordinate coordinate : findPaths(departure, arrival)) {
            if (board.isExistence(coordinate)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int dx = arrival.getX() - departure.getX();
        int dy = arrival.getY() - departure.getY();

        if (Math.abs(dx) == 2) {
            if (dx > 0) {
                return Set.of(departure.pickChangedCoordinate(1, 0));
            }
            return Set.of(departure.pickChangedCoordinate(-1, 0));
        }
        if (Math.abs(dy) == 2) {
            if (dy > 0) {
                return Set.of(departure.pickChangedCoordinate(0, 1));
            }
            return Set.of(departure.pickChangedCoordinate(0, -1));
        }
        throw new IllegalArgumentException("여기까지 못옴");
    }
}
