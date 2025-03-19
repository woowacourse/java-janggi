package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import java.util.List;

public class Ma implements Piece {

    private final JanggiCoordinate currCoordinate;
    private final Team team;

    public Ma(JanggiCoordinate currCoordinate, Team team) {
        this.currCoordinate = currCoordinate;
        this.team = team;
    }

    @Override
    public List<JanggiCoordinate> fromCurrPosition(JanggiBoard janggiBoard) {
        return Movement.availableMovePositions(currCoordinate, janggiBoard);
    }

    public JanggiCoordinate movePosition(JanggiCoordinate moveTo) {
        return currCoordinate.move(moveTo.getRow(), moveTo.getCol());
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
