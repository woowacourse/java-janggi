package janggi.team;

import janggi.board.TableOption;
import janggi.piece.Piece;

import java.util.Arrays;
import java.util.List;

public enum Team {
    CHO(10, 1),
    HAN(1, 2);

    private final int StartingRow;
    private final int turn;

    Team(int startingRow, int turn) {
        StartingRow = startingRow;
        this.turn = turn;
    }

    public Team turnOver() {
        return Arrays.stream(values()).filter(value ->
                ((turn % values().length) + 1) == value.turn
        ).findFirst().orElseThrow();
    }

    public List<Piece> generateTableSetPieces(TableOption option) {
        return option.generateTableSetPieces(this, StartingRow);
    }
}
