package domain.piece;

import domain.JanggiCoordinate;
import java.util.ArrayList;
import java.util.List;

public class Cha implements Piece {

    private final JanggiCoordinate currCoordinate;

    public Cha(JanggiCoordinate currCoordinate) {
        this.currCoordinate = currCoordinate;
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions() {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        int row = currCoordinate.getRow();
        int col = currCoordinate.getCol();
        for (int i = 1; i <= 9; i++) {
            if (i == row) {
                continue;
            }
            availablePositions.add(currCoordinate.move(i, col));
        }
        for (int i = 1; i <= 10; i++) {
            if (i == col) {
                continue;
            }
            availablePositions.add(currCoordinate.move(row, i));
        }
        return availablePositions;
    }

}
