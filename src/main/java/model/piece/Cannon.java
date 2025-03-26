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
        return calculatePositionOfMovement(departure, arrival);
    }

    //TODO : Depth 분리
    private List<Position> calculatePositionOfMovement(Position departure, Position arrival) {
        for (Movement movement : movements) {
            List<Position> temporaryPosition = new ArrayList<>();
            Position movedPosition = departure.copyOf();
            while (movedPosition.canMove(movement)) {
                temporaryPosition.add(movedPosition.move(movement));
                movedPosition = movedPosition.move(movement);
                if (movedPosition.equals(arrival)) {
                    return temporaryPosition;
                }
            }
        }
        throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
    }
}
