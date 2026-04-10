package board;

import java.util.HashMap;
import java.util.Map;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Position;

public final class SangSetup {

    public static Board initialize(final SangSetupType choSetupType, final SangSetupType hanSetupType) {
        final Map<Position, Piece> merged = new HashMap<>();
        merged.putAll(initialize(choSetupType, Side.CHO));
        merged.putAll(initialize(hanSetupType, Side.HAN));
        return new Board(merged);
    }

    private static Map<Position, Piece> initialize(final SangSetupType setupType, final Side side) {
        final Map<Position, Piece> pieces = new HashMap<>();
        putDefaultPieces(pieces, side);
        pieces.putAll(createSangMa(setupType, side));
        return pieces;
    }

    private static void putDefaultPieces(final Map<Position, Piece> board, final Side side) {
        board.putAll(createCha(side));
        board.putAll(createSa(side));
        board.putAll(createGung(side));
        board.putAll(createPo(side));
        board.putAll(createJolByeong(side));
    }

    private static Map<Position, Piece> createCha(final Side side) {
        return Map.of(
            toPosition(side, 0, 0), PieceType.CHA.create(side),
            toPosition(side, 0, 8), PieceType.CHA.create(side)
        );
    }

    private static Map<Position, Piece> createSa(final Side side) {
        return Map.of(
            toPosition(side, 0, 3), PieceType.SA.create(side),
            toPosition(side, 0, 5), PieceType.SA.create(side)
        );
    }

    private static Map<Position, Piece> createGung(final Side side) {
        return Map.of(
            toPosition(side, 1, 4), PieceType.GUNG.create(side)
        );
    }

    private static Map<Position, Piece> createPo(final Side side) {
        return Map.of(
            toPosition(side, 2, 1), PieceType.PO.create(side),
            toPosition(side, 2, 7), PieceType.PO.create(side)
        );
    }

    private static Map<Position, Piece> createJolByeong(final Side side) {
        return Map.of(
            toPosition(side, 3, 2), PieceType.JOL_BYEONG.create(side),
            toPosition(side, 3, 4), PieceType.JOL_BYEONG.create(side),
            toPosition(side, 3, 0), PieceType.JOL_BYEONG.create(side),
            toPosition(side, 3, 6), PieceType.JOL_BYEONG.create(side),
            toPosition(side, 3, 8), PieceType.JOL_BYEONG.create(side)
        );
    }

    private static Map<Position, Piece> createSangMa(final SangSetupType setupType, final Side side) {
        return Map.of(
            toPosition(side, 0, 1), setupType.createFirst(side),
            toPosition(side, 0, 2), setupType.createSecond(side),
            toPosition(side, 0, 6), setupType.createThird(side),
            toPosition(side, 0, 7), setupType.createFourth(side)
        );
    }

    private static Position toPosition(final Side side, final int choRow, final int choColumn) {
        final Position position = new Position(choRow, choColumn);
        if (side.isCho()) {
            return position;
        }
        return position.reverse();
    }
}