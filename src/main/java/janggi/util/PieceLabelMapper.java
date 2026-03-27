package janggi.util;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.Piece;
import janggi.domain.game.Side;
import java.util.Map;

public class PieceLabelMapper {
    private static final Map<PieceType, String> NAMES = Map.of(
            PieceType.GUARD, "사", PieceType.CHARIOT, "차", PieceType.CANNON, "포",
            PieceType.HORSE, "마", PieceType.ELEPHANT, "상", PieceType.CHO_SOLDIER, "졸",
            PieceType.HAN_SOLDIER, "병"
    );

    public static String toFullWidth(Piece piece) {
        return getPieceName(piece) + convertToFullWidthChar(piece.pieceNumber());
    }

    public static String toHalfWidth(Piece piece) {
        return getPieceName(piece) + piece.pieceNumber();
    }

    private static String getPieceName(Piece piece) {
        if (piece.type() == PieceType.PALACE) {
            return piece.side() == Side.CHO ? "초" : "한";
        }
        return NAMES.get(piece.type());
    }

    private static String convertToFullWidthChar(String halfWidthNumber) {
        int num = Integer.parseInt(halfWidthNumber);
        return String.valueOf((char) ('０' + num));
    }
}
