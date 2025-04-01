package model.piece.movement;

import static model.position.Movement.*;

import java.util.List;
import model.piece.PieceType;
import model.position.Movement;
import model.navigator.JumpMoveNavigator;
import model.piece.Piece;
import model.piece.Team;
import model.position.Position;

public class ElephantDirectionFinder implements DirectionFindable {

    private final JumpMoveNavigator jumpMoveNavigator = JumpMoveNavigator.getInstance();
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
        return jumpMoveNavigator.find(departure, arrival, movements);
    }
}
