package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CurrentBoard {
    private final List<List<PieceInfo>> currentBoard;

    public CurrentBoard(List<List<PieceInfo>> currentBoard) {
        this.currentBoard = currentBoard;
    }

    public static CurrentBoard from(Map<Position, Piece> board) {
        List<List<PieceInfo>> rows = new ArrayList<>();

        for(int row = 1; row <= 10; row++) {
            List<PieceInfo> currentRow = new ArrayList<>();
            for(int col = 1; col <= 9; col++) {
                Position position = new Position(row, col);
                currentRow.add(board.get(position).getPieceInfo());
            }
            rows.add(Collections.unmodifiableList(currentRow));
        }
        return new CurrentBoard(Collections.unmodifiableList(rows));
    }

    public List<List<PieceInfo>> getValues() {
        return currentBoard;
    }
}
