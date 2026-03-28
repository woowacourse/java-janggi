package board;

import java.util.HashMap;
import java.util.Map;
import pieces.Cha;
import pieces.EmptyPiece;
import pieces.Gung;
import pieces.JolByeong;
import pieces.Piece;
import pieces.Po;
import pieces.Sa;
import pieces.Side;
import position.Position;

public abstract class GeneralSangSetup implements SangSetup {

    private static final Piece EMPTY_PIECE = EmptyPiece.getInstance();
    private static final int ONE_SPACE = 1;
    private static final int CHO_START_ROW = 0;
    private static final int HAN_START_ROW = 5;
    private static final int BOARD_ROW_SIZE = 9;
    private static final int BOARD_COLUMN_SIZE = 8;

    @Override
    public Board initialize(Side side) {
        Map<Position, Piece> board = new HashMap<>();
        putEmptyArea(board, side);
        putDefaultPieces(board, side);
        board.putAll(getSangAndMaPositions(side));
        return new Board(board);
    }

    private void putEmptyArea(Map<Position, Piece> board, Side side) {
        int startRow = startRow(side);
        int endRow = endRow(side);

        for (int row = startRow; row <= endRow; row++) {
            for (int column = 0; column <= 8; column++) {
                board.put(new Position(row, column), EMPTY_PIECE);
            }
        }
    }

    private int startRow(Side side) {
        if (side.isCho()) {
            return CHO_START_ROW;
        }
        return HAN_START_ROW - ONE_SPACE;
    }

    private int endRow(Side side) {
        if (side.isCho()) {
            return HAN_START_ROW;
        }
        return BOARD_ROW_SIZE;
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