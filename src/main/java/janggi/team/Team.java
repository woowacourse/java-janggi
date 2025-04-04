package janggi.team;

import janggi.board.TableOption;
import janggi.piece.Piece;
import janggi.position.Position;

import java.util.Arrays;
import java.util.Map;

public enum Team {
    CHO(10, 1),
    HAN(1, 2);

    private final int startingRow;
    private final int turn;

    Team(int startingRow, int turn) {
        this.startingRow = startingRow;
        this.turn = turn;
    }

    public Team turnOver() {
        return Arrays.stream(values()).filter(value ->
                ((turn % values().length) + 1) == value.turn
        ).findFirst().orElseThrow();
    }

    public Map<Position, Piece> generateTableSetPieces(TableOption option) {
        return option.generateTableSetPieces(this, startingRow);
    }

    public boolean isMoveBack(Position startPosition, Position arrivedPosition) {
        return startPosition.calculateRowDistance(startingRow) > arrivedPosition.calculateRowDistance(startingRow);
    }
}
