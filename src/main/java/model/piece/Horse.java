package model.piece;

import static model.Movement.*;

import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Horse extends Piece {

    private final List<List<Movement>> movements = List.of(
        List.of(UP, UP_AND_DIAGONAL_UP_LEFT), List.of(UP, UP_AND_DIAGONAL_UP_RIGHT),
        List.of(DOWN, DOWN_AND_DIAGONAL_DOWN_LEFT), List.of(DOWN, DOWN_AND_DIAGONAL_DOWN_RIGHT),
        List.of(LEFT, LEFT_AND_DIAGONAL_DOWN_LEFT), List.of(LEFT, LEFT_AND_DIAGONAL_UP_LEFT),
        List.of(RIGHT, RIGHT_AND_DIAGONAL_DOWN_RIGHT), List.of(RIGHT, RIGHT_AND_DIAGONAL_UP_RIGHT));

    public Horse(Team team) {
        super(team);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public String getName() {
        if (getTeam() == Team.RED) {
            return "馬";
        }
        return "마";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<Movement> canArriveMovements = findCanArriveMovements(departure, arrival);
        return findDirectionOfArrival(departure, canArriveMovements);
    }

    private List<Position> findDirectionOfArrival(Position departure, List<Movement> canArriveMovements) {
        return canArriveMovements.stream()
            .map(departure::move)
            .toList();
    }

    private List<Movement> findCanArriveMovements(Position departure, Position arrival) {
        return movements.stream()
            .filter(movement -> departure.canMove(movement.getLast()))
            .filter(movement -> departure.move(movement.getLast()).equals(arrival))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
    }
}
