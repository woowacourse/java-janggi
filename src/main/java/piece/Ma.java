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
                .map(departure::moveBy)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    protected boolean canMoveConsideringObstacles(Board board, Coordinate departure, Coordinate arrival) {
        return findPaths(departure, arrival)
                .stream()
                .noneMatch(board::hasPiece);
    }

    @Override
    protected Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int deltaX = arrival.getX() - departure.getX();
        int deltaY = arrival.getY() - departure.getY();

        if (Math.abs(deltaX) == 2 && deltaY != 0) {
            if (deltaX > 0) {
                return Set.of(departure.moveBy(List.of(RIGHT)));
            }
            return Set.of(departure.moveBy(List.of(LEFT)));
        }
        if (Math.abs(deltaY) == 2 && deltaX != 0) {
            if (deltaY > 0) {
                return Set.of(departure.moveBy(List.of(DOWN)));
            }
            return Set.of(departure.moveBy(List.of(UP)));
        }

        throw new IllegalStateException("유효하지 않은 좌표입니다.");
    }

    @Override
    public String getName() {
        return "마";
    }
}
