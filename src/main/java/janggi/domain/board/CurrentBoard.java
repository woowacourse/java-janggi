package janggi.domain.board;

import static janggi.domain.Position.BOARD_END_COLS;
import static janggi.domain.Position.BOARD_END_ROWS;
import static janggi.domain.Position.BOARD_START_COLS;
import static janggi.domain.Position.BOARD_START_ROWS;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CurrentBoard {
    private final List<List<PieceAttribute>> currentBoard;

    public CurrentBoard(List<List<PieceAttribute>> currentBoard) {
        this.currentBoard = currentBoard;
    }

    public static CurrentBoard from(Map<Position, Piece> board) {
        List<List<PieceAttribute>> rows = new ArrayList<>();

        for(int row = BOARD_START_ROWS; row <= BOARD_END_ROWS; row++) {
            List<PieceAttribute> currentRow = new ArrayList<>();
            for(int col = BOARD_START_COLS; col <= BOARD_END_COLS; col++) {
                Position position = new Position(row, col);
                currentRow.add(board.get(position).getPieceInfo());
            }
            rows.add(Collections.unmodifiableList(currentRow));
        }
        return new CurrentBoard(Collections.unmodifiableList(rows));
    }

    public List<List<PieceAttribute>> getValues() {
        return currentBoard;
    }
}
