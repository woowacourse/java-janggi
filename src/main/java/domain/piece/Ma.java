package domain.piece;

import domain.Coordinate;
import domain.Team;
import domain.board.Board;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ma extends Piece {

    public Ma(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return Stream.of(
                        departure.pickChangedCoordinate(-2, -1),
                        departure.pickChangedCoordinate(-2, 1),
                        departure.pickChangedCoordinate(-1, -2),
                        departure.pickChangedCoordinate(-1, 2),
                        departure.pickChangedCoordinate(1, -2),
                        departure.pickChangedCoordinate(1, 2),
                        departure.pickChangedCoordinate(2, -1)
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    @Override
    protected boolean canMoveConsideringObstacles(Board board, Coordinate departure, Coordinate arrival) {
        return findPaths(departure, arrival)
                .stream()
                .noneMatch(board::isExistence);
    }

    @Override
    protected Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int dx = arrival.getX() - departure.getX();
        int dy = arrival.getY() - departure.getY();

        if (Math.abs(dx) == 2) {
            if (dx > 0) {
                return Set.of(departure.pickChangedCoordinate(1, 0).get());
            }
            return Set.of(departure.pickChangedCoordinate(-1, 0).get());
        }
        if (Math.abs(dy) == 2) {
            if (dy > 0) {
                return Set.of(departure.pickChangedCoordinate(0, 1).get());
            }
            return Set.of(departure.pickChangedCoordinate(0, -1).get());
        }
        throw new IllegalStateException("유효하지 않은 좌표입니다.");
    }
}
