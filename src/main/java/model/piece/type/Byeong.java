package model.piece.type;

import static model.position.Movement.*;

import java.util.List;
import model.position.Movement;
import model.navigator.LimitedBasicMoveNavigator;
import model.piece.Castle;
import model.piece.Piece;
import model.piece.Team;
import model.position.Position;

public class Byeong extends Piece {

    private static final int SCORE = 2;
    private static final String TYPE = "BYEONG";
    private final Castle castle;
    private final List<Movement> movements = List.of(DOWN, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(DIAGONAL_DOWN_LEFT,
        DIAGONAL_DOWN_RIGHT);
    private final LimitedBasicMoveNavigator limitedBasicMoveNavigator;

    public Byeong() {
        super(Team.RED, SCORE, TYPE);
        this.castle = Castle.getInstance();
        this.limitedBasicMoveNavigator = LimitedBasicMoveNavigator.getInstance();
    }

    @Override
    public String getName() {
        return "兵";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<Movement> decidedMovements = castle.decideMovements(movements,
            movementsInCastle, departure, arrival);
        return limitedBasicMoveNavigator.find(departure, arrival, decidedMovements);
    }
}
