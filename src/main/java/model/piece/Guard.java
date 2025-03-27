package model.piece;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Guard extends Piece {

    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(
        DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT,
        DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT);
    private final Area area;
    private final LimitedBasicMoveNavigator limitedBasicMoveNavigator;

    public Guard(Team team) {
        super(team);
        this.area = new Area();
        this.limitedBasicMoveNavigator = new LimitedBasicMoveNavigator();
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
        validateOutOfCastle(arrival);
        List<Movement> decidedMovements = decideMovements(departure, arrival);
        return limitedBasicMoveNavigator.find(departure, arrival, decidedMovements);
    }

    private void validateOutOfCastle(Position arrival) {
        if (!area.inCastle(arrival)) {
            throw new IllegalArgumentException("해당 기물은 궁성 밖으로 이동할 수 없습니다.");
        }
    }

    private List<Movement> decideMovements(Position departure, Position arrival) {
        List<Movement> decidedMovements = new ArrayList<>(movements);
        if (area.canMoveDiagonal(departure) && area.inCastle(arrival)) {
            decidedMovements.addAll(movementsInCastle);
        }
        return decidedMovements;
    }
}
