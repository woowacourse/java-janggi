package board;

import java.util.HashMap;
import java.util.Map;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Position;

public final class SangSetup {

    private static final int BOARD_ROW_SIZE = 9;
    private static final int BOARD_COLUMN_SIZE = 8;

    public static Board initialize(SangSetupType setupType, Side side) {
        Map<Position, Piece> board = new HashMap<>();
        putDefaultPieces(board, side);
        board.putAll(createSangMa(setupType, side));
        return new Board(board);
    }

    private static void putDefaultPieces(Map<Position, Piece> board, Side side) {
        board.putAll(createCha(side));
        board.putAll(createSa(side));
        board.putAll(createGung(side));
        board.putAll(createPo(side));
        board.putAll(createJolByeong(side));
    }

    private static Map<Position, Piece> createCha(Side side) {
        return Map.of(
            toPosition(side, 0, 0), PieceType.CHA.create(side),
            toPosition(side, 0, 8), PieceType.CHA.create(side)
        );
    }

    private static Map<Position, Piece> createSa(Side side) {
        return Map.of(
            toPosition(side, 0, 3), PieceType.SA.create(side),
            toPosition(side, 0, 5), PieceType.SA.create(side)
        );
    }

    private static Map<Position, Piece> createGung(Side side) {
        return Map.of(
            toPosition(side, 1, 4), PieceType.GUNG.create(side)
        );
    }

    private static Map<Position, Piece> createPo(Side side) {
        return Map.of(
            toPosition(side, 2, 1), PieceType.PO.create(side),
            toPosition(side, 2, 7), PieceType.PO.create(side)
        );
    }

    private static Map<Position, Piece> createJolByeong(Side side) {
        return Map.of(
            toPosition(side, 3, 2), PieceType.JOL_BYEONG.create(side),
            toPosition(side, 3, 4), PieceType.JOL_BYEONG.create(side),
            toPosition(side, 3, 0), PieceType.JOL_BYEONG.create(side),
            toPosition(side, 3, 6), PieceType.JOL_BYEONG.create(side),
            toPosition(side, 3, 8), PieceType.JOL_BYEONG.create(side)
        );
    }

    private static Map<Position, Piece> createSangMa(SangSetupType setupType, Side side) {
        return Map.of(
            toPosition(side, 0, 1), setupType.createFirst(side),
            toPosition(side, 0, 2), setupType.createSecond(side),
            toPosition(side, 0, 6), setupType.createThird(side),
            toPosition(side, 0, 7), setupType.createFourth(side)
        );
    }

    private static Position toPosition(Side side, final int choRow, final int choColumn) {
        Position position = new Position(choRow, choColumn);
        if (side.isCho()) {
            return position;
        }
        return reverse(position);
    }

    private static Position reverse(Position position) {
        return new Position(
            BOARD_ROW_SIZE - position.row().index(),
            BOARD_COLUMN_SIZE - position.column().index()
        );
    }
}