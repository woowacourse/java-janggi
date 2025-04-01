package model.piece.movement;

import static model.position.Movement.*;

import java.util.List;
import model.position.Movement;
import model.navigator.JumpMoveStrategy;
import model.position.Position;

public class ElephantDirectionFinder implements DirectionFinder {

    private final JumpMoveStrategy jumpMoveStrategy = JumpMoveStrategy.getInstance();
    private final List<List<Movement>> movements = List.of(
        List.of(UP, UP_AND_DIAGONAL_UP_LEFT, UP_AND_DOUBLE_DIAGONAL_UP_LEFT),
        List.of(UP, UP_AND_DIAGONAL_UP_RIGHT, UP_AND_DOUBLE_DIAGONAL_UP_RIGHT),
        List.of(DOWN, DOWN_AND_DIAGONAL_DOWN_LEFT, DOWN_AND_DOUBLE_DIAGONAL_DOWN_LEFT),
        List.of(DOWN, DOWN_AND_DIAGONAL_DOWN_RIGHT, DOWN_AND_DOUBLE_DIAGONAL_DOWN_RIGHT),
        List.of(LEFT, LEFT_AND_DIAGONAL_DOWN_LEFT, LEFT_AND_DOUBLE_DIAGONAL_DOWN_LEFT),
        List.of(LEFT, LEFT_AND_DIAGONAL_UP_LEFT, LEFT_AND_DOUBLE_DIAGONAL_UP_LEFT),
        List.of(RIGHT, RIGHT_AND_DIAGONAL_DOWN_RIGHT, RIGHT_AND_DOUBLE_DIAGONAL_DOWN_RIGHT),
        List.of(RIGHT, RIGHT_AND_DIAGONAL_UP_RIGHT, RIGHT_AND_DOUBLE_DIAGONAL_UP_RIGHT));

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        return jumpMoveStrategy.find(departure, arrival, movements);
    }
}
