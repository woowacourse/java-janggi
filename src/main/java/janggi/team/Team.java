package janggi.team;

import janggi.board.TableOption;
import janggi.piece.Piece;

import java.util.List;

public enum Team {
    CHO(10),
    HAN(1);

    private final int StartingRow;

    Team(int row) {
        this.StartingRow = row;
    }

    // TODO 테스트 작성
    public List<Piece> generateTableSetPieces(TableOption option) {
        return option.generateTableSetPieces(this, StartingRow);
    }
}
