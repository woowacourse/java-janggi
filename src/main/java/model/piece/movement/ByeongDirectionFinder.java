package model.piece.movement;

import static model.position.Movement.*;

import java.util.List;
import model.position.Movement;
import model.navigator.LimitedBasicMoveNavigator;
import model.piece.Castle;
import model.position.Position;

public class ByeongDirectionFinder implements DirectionFindable {

    private final List<Movement> movements = List.of(DOWN, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT);
    private final Castle castle = Castle.getInstance();
    private final LimitedBasicMoveNavigator limitedBasicMoveNavigator = LimitedBasicMoveNavigator.getInstance();

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<Movement> decidedMovements = castle.decideMovements(movements,
            movementsInCastle, departure, arrival);
        return limitedBasicMoveNavigator.find(departure, arrival, decidedMovements);
    }
}
