package model.piece.type;

import static model.position.Movement.DIAGONAL_UP_LEFT;
import static model.position.Movement.DIAGONAL_UP_RIGHT;
import static model.position.Movement.LEFT;
import static model.position.Movement.RIGHT;
import static model.position.Movement.UP;

import java.util.List;
import model.position.Movement;
import model.navigator.LimitedBasicMoveNavigator;
import model.piece.Castle;
import model.piece.Piece;
import model.piece.Team;
import model.position.Position;

public class Jol extends Piece {

    private static final int SCORE = 2;
    private static final String TYPE = "JOL";
    private final Castle castle;
    private final List<Movement> movements = List.of(UP, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT);
    private final LimitedBasicMoveNavigator limitedBasicMoveNavigator;

    public Jol() {
        super(Team.GREEN, SCORE, TYPE);
        this.castle = Castle.getInstance();
        this.limitedBasicMoveNavigator = LimitedBasicMoveNavigator.getInstance();
    }

    @Override
    public String getName() {
        return "졸";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<Movement> decidedMovements = castle.decideMovements(movements, movementsInCastle,
            departure, arrival);
        return limitedBasicMoveNavigator.find(departure, arrival, decidedMovements);
    }
}
