package model.piece.type;

import static model.position.Movement.*;

import java.util.List;
import model.position.Movement;
import model.navigator.LimitedBasicMoveNavigator;
import model.piece.Castle;
import model.piece.Piece;
import model.piece.Team;
import model.position.Position;

public class General extends Piece {

    private static final int SCORE = 0;
    private static final String TYPE = "GENERAL";
    private final Castle castle;
    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(
        DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT,
        DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT);
    private final LimitedBasicMoveNavigator limitedBasicMoveNavigator;

    public General(Team team) {
        super(team, SCORE, TYPE);
        this.castle = Castle.getInstance();
        this.limitedBasicMoveNavigator = LimitedBasicMoveNavigator.getInstance();
    }

    @Override
    public String getName() {
        if (getTeam() == Team.RED) {
            return "漢";
        }
        return "초";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        validateOutOfCastle(arrival);
        List<Movement> decidedMovements = castle.decideMovements(movements, movementsInCastle, departure, arrival);
        return limitedBasicMoveNavigator.find(departure, arrival, decidedMovements);
    }

    @Override
    public boolean isGeneral() {
        return true;
    }

    private void validateOutOfCastle(Position arrival) {
        if (!castle.inCastle(arrival)) {
            throw new IllegalArgumentException("해당 기물은 궁성 밖으로 이동할 수 없습니다.");
        }
    }
}
