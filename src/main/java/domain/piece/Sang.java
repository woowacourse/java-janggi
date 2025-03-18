package domain.piece;

import domain.JanggiCoordinate;
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

    private final JanggiCoordinate currCoordinate;

    public Sang(JanggiCoordinate currCoordinate) {
        this.currCoordinate = currCoordinate;
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
}
