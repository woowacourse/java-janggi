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

public class Sang extends Piece {

    public Sang(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return Stream.<List<MoveVector>>of(
                        List.of(UP, RIGHT_UP, RIGHT_UP),
                        List.of(UP, LEFT_UP, LEFT_UP),
                        List.of(DOWN, RIGHT_DOWN, RIGHT_DOWN),
                        List.of(DOWN, LEFT_DOWN, LEFT_DOWN),
                        List.of(RIGHT, RIGHT_UP, RIGHT_UP),
                        List.of(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
                        List.of(LEFT, LEFT_UP, LEFT_UP),
                        List.of(LEFT, LEFT_DOWN, LEFT_DOWN)
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
        return Stream.<List<MoveVector>>of(
                        List.of(LEFT, LEFT_UP),
                        List.of(LEFT, LEFT_DOWN),
                        List.of(RIGHT, RIGHT_UP),
                        List.of(RIGHT, RIGHT_DOWN),
                        List.of(UP, LEFT_UP),
                        List.of(DOWN, LEFT_DOWN),
                        List.of(UP, RIGHT_UP),
                        List.of(DOWN, RIGHT_DOWN)
                )
                .filter(path -> isMatchingDirection(path, departure, arrival))
                .findFirst()
                .map(moveVectors -> toCoordinates(moveVectors, departure))
                .orElseThrow(() -> new IllegalStateException("유효하지 않은 좌표입니다."));
    }

    private boolean isMatchingDirection(List<MoveVector> path, Coordinate departure, Coordinate arrival) {
        List<MoveVector> pathIncludeArrival = Stream.concat(
                path.stream(),
                Stream.of(path.getLast())
        ).toList();

        int totalDeltaX = pathIncludeArrival.stream()
                .mapToInt(MoveVector::deltaX)
                .sum();
        int totalDeltaY = pathIncludeArrival.stream()
                .mapToInt(MoveVector::deltaY)
                .sum();

        return departure.getX() + totalDeltaX == arrival.getX()
                && departure.getY() + totalDeltaY == arrival.getY();
    }

    private Set<Coordinate> toCoordinates(List<MoveVector> moveVectors, Coordinate departure) {
        return Set.of(
                departure.moveBy(List.of(moveVectors.getFirst())),
                departure.moveBy(List.of(moveVectors.getFirst(), moveVectors.getLast()))
        );
    }

    @Override
    public String getName() {
        return "상";
    }
}
