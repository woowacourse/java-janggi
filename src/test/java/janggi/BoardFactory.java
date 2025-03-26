package janggi;

import static janggi.board.Board.COLUMN;
import static janggi.board.Board.ROW;

import janggi.board.Board;
import janggi.piece.Camp;
import janggi.piece.Empty;
import janggi.piece.Piece;
import janggi.position.Position;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public static Board emptyBoard(Camp camp) {
        return new Board(initializeCells(), camp);
    }

    private static Map<Position, Piece> initializeCells() {
        Map<Position, Piece> cells = new HashMap<>(COLUMN * ROW);
        for (int x = 0; x < COLUMN; x++) {
            for (int y = 0; y < ROW; y++) {
                cells.put(new Position(x, y), Empty.INSTANCE);
            }
        }
        return cells;
    }
}
