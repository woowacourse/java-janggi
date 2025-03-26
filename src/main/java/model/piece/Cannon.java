package model.piece;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Cannon extends Piece {

    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public String getName() {
        if (getTeam() == Team.RED) {
            return "包";
        }
        return "포";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        return movements.stream()
            .map(movement -> findDirectionByMovement(departure, arrival, movement))
            .filter(moveDirections -> moveDirections.contains(arrival))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
    }

    private List<Position> findDirectionByMovement(Position departure, Position arrival, Movement movement) {
        List<Position> movedPosition = new ArrayList<>();
        while (departure.canMove(movement) && !movedPosition.contains(arrival)) {
            departure = departure.move(movement);
            movedPosition.add(departure);
        }
        return movedPosition;
    }
}
