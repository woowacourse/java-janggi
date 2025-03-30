package model.piece;

import static model.Movement.*;

import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Chariot extends Piece {

    private static final int SCORE = 13;
    private static final String TYPE = "CHARIOT";
    private final Castle castle;
    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(
        DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT,
        DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT);
    private final UnlimitedBasicMoveNavigator unlimitedBasicMoveNavigator;

    public Chariot(Team team) {
        super(team, SCORE, TYPE);
        this.castle = Castle.getInstance();
        this.unlimitedBasicMoveNavigator = UnlimitedBasicMoveNavigator.getInstance();
    }

    @Override
    public String getName() {
        if (getTeam() == Team.RED) {
            return "車";
        }
        return "차";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<Movement> decidedMovements = castle.decideMovements(movements,
            movementsInCastle, departure, arrival);
        return unlimitedBasicMoveNavigator.find(departure, arrival, decidedMovements);
    }
}
