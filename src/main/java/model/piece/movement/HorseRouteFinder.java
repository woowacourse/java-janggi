package model.piece.movement;

import static model.position.Movement.*;

import java.util.List;
import model.position.Movement;
import model.strategy.JumpMoveStrategy;
import model.position.Position;

public class HorseRouteFinder implements RouteFinder {

    private final List<List<Movement>> movements = List.of(
        List.of(UP, UP_AND_DIAGONAL_UP_LEFT), List.of(UP, UP_AND_DIAGONAL_UP_RIGHT),
        List.of(DOWN, DOWN_AND_DIAGONAL_DOWN_LEFT), List.of(DOWN, DOWN_AND_DIAGONAL_DOWN_RIGHT),
        List.of(LEFT, LEFT_AND_DIAGONAL_DOWN_LEFT), List.of(LEFT, LEFT_AND_DIAGONAL_UP_LEFT),
        List.of(RIGHT, RIGHT_AND_DIAGONAL_DOWN_RIGHT), List.of(RIGHT, RIGHT_AND_DIAGONAL_UP_RIGHT));
    private final JumpMoveStrategy jumpMoveStrategy = JumpMoveStrategy.getInstance();

    @Override
    public List<Position> calculateAllRoute(Position departure, Position arrival) {
        return jumpMoveStrategy.findRoute(departure, arrival, movements);
    }


}
