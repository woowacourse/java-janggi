package janggi.util;

import janggi.domain.game.Side;
import janggi.domain.piece.PieceType;
import java.util.Map;
import java.util.function.Function;

public class PieceLabelMapper {

    private static final Map<Side, String> SOLDIER_NAMES = Map.of(
            Side.CHO, "졸",
            Side.HAN, "병"
    );

    private static final Map<PieceType, Function<Side, String>> NAMES = Map.of(
            PieceType.GUARD, side -> "사",
            PieceType.CHARIOT, side -> "차",
            PieceType.CANNON, side -> "포",
            PieceType.HORSE, side -> "마",
            PieceType.ELEPHANT, side -> "상",
            PieceType.PALACE, SideDisplayNameMapper::toDisplayName,
            PieceType.SOLDIER, SOLDIER_NAMES::get
    );

    public static String toLabel(Side side, PieceType type, String pieceNumber) {
        String pieceTypeLabel = findPieceTypeLabel(side, type);
        String pieceNumberLabel = convertToFullWidthChar(pieceNumber);
        return pieceTypeLabel + pieceNumberLabel;
    }

    private static String findPieceTypeLabel(Side side, PieceType type) {
        Function<Side, String> pieceTypeLabelFunction = NAMES.get(type);
        return pieceTypeLabelFunction.apply(side);
    }

    private static String convertToFullWidthChar(String halfWidthNumber) {
        int num = Integer.parseInt(halfWidthNumber);
        return String.valueOf((char) ('０' + num));
    }
}
