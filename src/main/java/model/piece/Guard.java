package model.piece;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Guard extends Piece {

    private static final int SCORE = 3;
    private static final String TYPE = "GUARD";
    private final Castle castle;
    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(
        DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT,
        DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT);
    private final LimitedBasicMoveNavigator limitedBasicMoveNavigator;

    public Guard(Team team) {
        super(team, SCORE, TYPE);
        this.castle = new Castle();
        this.limitedBasicMoveNavigator = new LimitedBasicMoveNavigator();
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
        List<Movement> decidedMovements = castle.decideMovements(movements, movementsInCastle, departure, arrival);
        return limitedBasicMoveNavigator.find(departure, arrival, decidedMovements);
    }

    private void validateOutOfCastle(Position arrival) {
        if (!castle.inCastle(arrival)) {
            throw new IllegalArgumentException("해당 기물은 궁성 밖으로 이동할 수 없습니다.");
        }
    }
}
