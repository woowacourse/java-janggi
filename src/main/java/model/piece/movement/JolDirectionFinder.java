package model.piece.movement;

import static model.position.Movement.DIAGONAL_UP_LEFT;
import static model.position.Movement.DIAGONAL_UP_RIGHT;
import static model.position.Movement.LEFT;
import static model.position.Movement.RIGHT;
import static model.position.Movement.UP;

import java.util.List;
import model.piece.PieceType;
import model.position.Movement;
import model.navigator.LimitedBasicMoveNavigator;
import model.piece.Castle;
import model.piece.Piece;
import model.piece.Team;
import model.position.Position;

public class JolDirectionFinder implements DirectionFindable {

    private final Castle castle = Castle.getInstance();
    private final LimitedBasicMoveNavigator limitedBasicMoveNavigator = LimitedBasicMoveNavigator.getInstance();
    private final List<Movement> movements = List.of(UP, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT);

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<Movement> decidedMovements = castle.decideMovements(movements, movementsInCastle,
            departure, arrival);
        return limitedBasicMoveNavigator.find(departure, arrival, decidedMovements);
    }
}
