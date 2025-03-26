package model.piece;

import static model.Movement.*;

import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Guard extends Piece {

    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);

    public Guard(Team team) {
        super(team);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public String getName() {
        if (getTeam() == Team.RED) {
            return "士";
        }
        return "사";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<Position> arrivedDirection = findDirectionOfArrival(departure, arrival);
        if (arrivedDirection.isEmpty()) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        return arrivedDirection;
    }

    private List<Position> findDirectionOfArrival(Position departure, Position arrival){
        return movements.stream()
            .filter(departure::canMove)
            .filter(movement -> departure.move(movement).equals(arrival))
            .map(departure::move)
            .toList();
    }
}
