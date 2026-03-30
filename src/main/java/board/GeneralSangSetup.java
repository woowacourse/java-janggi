package board;

import java.util.HashMap;
import java.util.Map;
import pieces.Cha;
import pieces.Gung;
import pieces.JolByeong;
import pieces.Piece;
import pieces.Po;
import pieces.Sa;
import pieces.Side;
import position.Position;

public abstract class GeneralSangSetup implements SangSetup {

    private static final int BOARD_ROW_SIZE = 9;
    private static final int BOARD_COLUMN_SIZE = 8;

    @Override
    public Board initialize(Side side) {
        Map<Position, Piece> board = new HashMap<>();
        putDefaultPieces(board, side);
        board.putAll(getSangAndMaPositions(side));
        return new Board(board);
    }

    private void putDefaultPieces(Map<Position, Piece> board, Side side) {
        board.put(toPosition(side, 0, 0), new Cha(side));
        board.put(toPosition(side, 0, 3), new Sa(side));
        board.put(toPosition(side, 1, 4), new Gung(side));
        board.put(toPosition(side, 0, 5), new Sa(side));
        board.put(toPosition(side, 0, 8), new Cha(side));

        board.put(toPosition(side, 2, 1), new Po(side));
        board.put(toPosition(side, 2, 7), new Po(side));

        board.put(toPosition(side, 3, 0), new JolByeong(side));
        board.put(toPosition(side, 3, 2), new JolByeong(side));
        board.put(toPosition(side, 3, 4), new JolByeong(side));
        board.put(toPosition(side, 3, 6), new JolByeong(side));
        board.put(toPosition(side, 3, 8), new JolByeong(side));
    }

    protected Position toPosition(Side side, int choRow, int choColumn) {
        Position position = new Position(choRow, choColumn);
        if (side.isCho()) {
            return position;
        }
        return reverse(position);
    }

    private Position reverse(Position position) {
        return new Position(
            BOARD_ROW_SIZE - position.row().index(),
            BOARD_COLUMN_SIZE - position.column().index()
        );
    }

    protected abstract Map<Position, Piece> getSangAndMaPositions(Side side);
}