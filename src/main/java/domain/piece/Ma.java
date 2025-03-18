package domain.piece;

import domain.JanggiCoordinate;
import java.util.ArrayList;
import java.util.List;

public class Ma implements Piece {
    private static final List<JanggiCoordinate> OFFSET_MOVEMENT = List.of(
            new JanggiCoordinate(2, 1),
            new JanggiCoordinate(2, -1),
            new JanggiCoordinate(-2, 1),
            new JanggiCoordinate(-2, -1),
            new JanggiCoordinate(1, 2),
            new JanggiCoordinate(1, -2),
            new JanggiCoordinate(-1, 2),
            new JanggiCoordinate(-1, -2)
    );

    private final JanggiCoordinate currCoordinate;
    private final Team team;

    public Ma(JanggiCoordinate currCoordinate, Team team) {
        this.currCoordinate = currCoordinate;
        this.team = team;
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions() {
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
}
