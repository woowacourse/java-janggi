package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import java.util.ArrayList;
import java.util.List;

public class Sang implements Piece {


    private static final List<JanggiCoordinate> OFFSET_MOVEMENT = List.of(
            new JanggiCoordinate(3, 2),
            new JanggiCoordinate(3, -2),
            new JanggiCoordinate(-3, 2),
            new JanggiCoordinate(-3, -2),
            new JanggiCoordinate(2, 3),
            new JanggiCoordinate(2, -3),
            new JanggiCoordinate(-2, 3),
            new JanggiCoordinate(-2, -3)
    );

    private final Team team;
    private final JanggiCoordinate currCoordinate;

    public Sang(JanggiCoordinate currCoordinate, Team team) {
        this.team = team;
        this.currCoordinate = currCoordinate;
    }

    @Override
    public List<JanggiCoordinate> fromCurrPosition(JanggiBoard board) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        for (JanggiCoordinate coordinate : OFFSET_MOVEMENT) {
            availablePositions.add(movePosition(coordinate));
        }
        return availablePositions;
    }

    public JanggiCoordinate movePosition(JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
