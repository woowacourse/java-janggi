package model.piece;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Byeong extends Piece {

    /***
     * Area를 각각의 piece가 들고있을 필요는 없을 것 같음. 책임 분리를 생각해보자.
     */
    private static final int SCORE = 2;

    private final List<Movement> movements = List.of(DOWN, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT);
    private final Area area;
    private final LimitedBasicMoveNavigator limitedBasicMoveNavigator;

    public Byeong() {
        super(Team.RED, SCORE);
        this.area = new Area();
        this.limitedBasicMoveNavigator = new LimitedBasicMoveNavigator();
    }

    @Override
    public String getName() {
        return "兵";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<Movement> decidedMovements = decideMovements(departure, arrival);
        return limitedBasicMoveNavigator.find(departure, arrival, decidedMovements);
    }

    private List<Movement> decideMovements(Position departure, Position arrival) {
        List<Movement> decidedMovements = new ArrayList<>(movements);
        if (area.canMoveDiagonal(departure) && area.inCastle(arrival)) {
            decidedMovements.addAll(movementsInCastle);
        }
        return decidedMovements;
    }
}
