package janggi.util;

import janggi.domain.game.Side;
import janggi.domain.piece.PieceType;
import java.util.Map;

public class PieceLabelMapper {

    private static final Map<PieceType, String> NAMES = Map.of(
            PieceType.GUARD, "사", 
            PieceType.CHARIOT, "차", 
            PieceType.CANNON, "포",
            PieceType.HORSE, "마", 
            PieceType.ELEPHANT, "상", 
            PieceType.CHO_SOLDIER, "졸",
            PieceType.HAN_SOLDIER, "병"
    );

    public static String toLabel(Side side, PieceType type, String pieceNumber) {
        String pieceTypeLabel = findPieceTypeLabel(side, type);
        String pieceNumberLabel = convertToFullWidthChar(pieceNumber);
        return pieceTypeLabel + pieceNumberLabel; // "포0", "초0" 형태로 완벽히 평탄화
    }

    private static String findPieceTypeLabel(Side side, PieceType type) {
        if (type == PieceType.PALACE) {
            return SideDisplayNameMapper.toDisplayName(side);
        }
        return NAMES.get(type);
    }

    private static String convertToFullWidthChar(String halfWidthNumber) {
        int num = Integer.parseInt(halfWidthNumber);
        return String.valueOf((char) ('０' + num));
    }
}
