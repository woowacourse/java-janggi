package janggi.team;

import janggi.board.TableOption;
import janggi.piece.Piece;
import janggi.position.Row;

import java.util.List;

public enum Team {
    CHO(new Row(10)),
    HAN(new Row(1));

    private final Row StartingRow;

    Team(Row row) {
        this.StartingRow = row;
    }

    // TODO 테스트 작성
    public List<Piece> generateTableSetPieces(TableOption option) {
        return option.generateTableSetPieces(this, StartingRow);
    }
}
