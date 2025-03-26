package model.piece;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Byeong extends Piece {

    private final List<Movement> movements = List.of(DOWN, LEFT, RIGHT);

    public Byeong() {
        super(Team.RED);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public String getName() {
        return "兵";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<Position> temporaryPosition = new ArrayList<>();
        calculatePositionOfMovement(departure, temporaryPosition);
        return findArrivalDirection(arrival, temporaryPosition);
    }

    private void calculatePositionOfMovement(Position departure, List<Position> temporaryPosition) {
        for (Movement movement : movements) {
            addMoveByDepartment(departure, temporaryPosition, movement);
        }
    }

    private void addMoveByDepartment(Position departure, List<Position> temporaryPosition, Movement movement) {
        if (!departure.canMove(movement)) {
            return;
        }
        temporaryPosition.add(departure.move(movement));
    }

    private List<Position> findArrivalDirection(Position arrival,
        List<Position> temporaryPosition) {
        return temporaryPosition.stream()
            .filter(position -> position.equals(arrival))
            .findFirst()
            .map(List::of)
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
    }
}
