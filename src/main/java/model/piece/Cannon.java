package model.piece;

import static model.Movement.*;

import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Cannon extends Piece {

    private static final int SCORE = 7;
    private static final String TYPE = "CANNON";
    private final Castle castle;
    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final UnlimitedBasicMoveNavigator unlimitedBasicMoveNavigator;
    private final List<Movement> movementsInCastle = List.of(
        DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT,
        DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT);

    public Cannon(Team team) {
        super(team, SCORE, TYPE);
        this.castle = Castle.getInstance();
        this.unlimitedBasicMoveNavigator = UnlimitedBasicMoveNavigator.getInstance();
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
        List<Movement> decidedMovements = castle.decideMovements(movements,
            movementsInCastle, departure, arrival);
        return unlimitedBasicMoveNavigator.find(departure, arrival, decidedMovements);
    }
}
