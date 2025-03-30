package piece.validator;

import static coordinate.CrossMoveVector.DOWN;
import static coordinate.CrossMoveVector.LEFT;
import static coordinate.CrossMoveVector.RIGHT;
import static coordinate.CrossMoveVector.UP;
import static coordinate.DiagonalMoveVector.LEFT_DOWN;
import static coordinate.DiagonalMoveVector.LEFT_UP;
import static coordinate.DiagonalMoveVector.RIGHT_DOWN;
import static coordinate.DiagonalMoveVector.RIGHT_UP;

import board.Board;
import coordinate.Coordinate;
import coordinate.MoveVector;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class SangPathValidator implements PathValidator {

    @Override
    public boolean validate(Board board, Coordinate departure, Coordinate arrival) {
        return findPaths(departure, arrival)
                .stream()
                .noneMatch(board::hasPiece);
    }

    private Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
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
}
