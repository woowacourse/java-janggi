package piece;

import static coordinate.DiagonalDirection.LEFT_DOWN;
import static coordinate.DiagonalDirection.LEFT_UP;
import static coordinate.DiagonalDirection.RIGHT_DOWN;
import static coordinate.DiagonalDirection.RIGHT_UP;
import static coordinate.Direction.DOWN;
import static coordinate.Direction.LEFT;
import static coordinate.Direction.RIGHT;
import static coordinate.Direction.UP;

import board.Board;
import coordinate.Coordinate;
import coordinate.MoveVector;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import team.Team;

public class Ma extends Piece {

    public Ma(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return Stream.<List<MoveVector>>of(
                        List.of(UP, RIGHT_UP),
                        List.of(UP, LEFT_UP),
                        List.of(DOWN, RIGHT_DOWN),
                        List.of(DOWN, LEFT_DOWN),
                        List.of(RIGHT, RIGHT_UP),
                        List.of(RIGHT, RIGHT_DOWN),
                        List.of(LEFT, LEFT_UP),
                        List.of(LEFT, LEFT_DOWN)
                )
                .map(departure::pickChangedCoordinate)
                .filter(Objects::nonNull)
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

    @Override
    public String getName() {
        return "마";
    }
}
