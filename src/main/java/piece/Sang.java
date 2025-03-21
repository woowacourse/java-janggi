package piece;

import board.Board;
import coordinate.Coordinate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import team.Team;

public class Sang extends Piece {

    public Sang(Team team) {
        super(team);
    }

    @Override
    protected Set<coordinate.Coordinate> findMovableCandidates(Coordinate departure) {
        return Stream.of(
                        departure.pickChangedCoordinate(2, -3), // 상우
                        departure.pickChangedCoordinate(2, 3), // 하우
                        departure.pickChangedCoordinate(-2, -3), //상좌
                        departure.pickChangedCoordinate(-2, 3), //하좌
                        departure.pickChangedCoordinate(3, -2), // 우상
                        departure.pickChangedCoordinate(3, 2), //우하
                        departure.pickChangedCoordinate(-3, -2), //좌상
                        departure.pickChangedCoordinate(-3, 2) //좌하
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
    protected Set<coordinate.Coordinate> findPaths(coordinate.Coordinate departure, Coordinate arrival) {
        int dx = arrival.getX() - departure.getX();
        int dy = arrival.getY() - departure.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            //좌우시작
            if (dx < 0 && dy < 0) {
                //좌상
                return Set.of(departure.pickChangedCoordinate(-1, 0).get(),
                        departure.pickChangedCoordinate(-2, -1).get());
            } else if (dx < 0 && dy > 0) {
                //좌하
                return Set.of(departure.pickChangedCoordinate(-1, 0).get(),
                        departure.pickChangedCoordinate(-2, 1).get());
            } else if (dx > 0 && dy < 0) {
                //우상
                return Set.of(departure.pickChangedCoordinate(1, 0).get(),
                        departure.pickChangedCoordinate(2, -1).get());
            } else if (dx > 0 && dy > 0) {
                //우하
                return Set.of(departure.pickChangedCoordinate(1, 0).get(),
                        departure.pickChangedCoordinate(2, 1).get());
            }
        } else {
            //상하시작
            if (dx < 0 && dy < 0) {
                //상좌
                return Set.of(departure.pickChangedCoordinate(0, -1).get(),
                        departure.pickChangedCoordinate(-1, -2).get());
            } else if (dx < 0 && dy > 0) {
                //하좌
                return Set.of(departure.pickChangedCoordinate(0, 1).get(),
                        departure.pickChangedCoordinate(-1, 2).get());
            } else if (dx > 0 && dy < 0) {
                //상우
                return Set.of(departure.pickChangedCoordinate(0, -1).get(),
                        departure.pickChangedCoordinate(1, -2).get());
            } else if (dx > 0 && dy > 0) {
                //하우
                return Set.of(departure.pickChangedCoordinate(0, 1).get(),
                        departure.pickChangedCoordinate(1, 2).get());
            }
        }
        throw new IllegalStateException("유효하지 않은 좌표입니다.");
    }
}
