package janggi.view;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.PieceDTO;
import janggi.domain.game.Side;
import java.util.Map;

public class PieceLabelFormatter {
    private static final Map<PieceType, String> NAMES = Map.of(
            PieceType.GUARD, "사",
            PieceType.CHARIOT, "차",
            PieceType.CANNON, "포",
            PieceType.HORSE, "마",
            PieceType.ELEPHANT, "상"
    );

    public static String toFullWidth(PieceDTO pieceDTO) {
        return getPieceName(pieceDTO) + convertToFullWidthChar(pieceDTO.pieceNumber());
    }

    private static String getPieceName(PieceDTO pieceDTO) {
        if (pieceDTO.pieceType() == PieceType.GENERAL) {
            return toPalaceName(pieceDTO.side());
        }

        if (pieceDTO.pieceType() == PieceType.SOLDIER) {
            return toSoldierName(pieceDTO.side());
        }

        return NAMES.get(pieceDTO.pieceType());
    }

    private static String toPalaceName(Side side) {
        if (side == Side.CHO) {
            return "초";
        }
        return "한";
    }

    private static String toSoldierName(Side side) {
        if (side == Side.CHO) {
            return "졸";
        }
        return "병";
    }

    private static String convertToFullWidthChar(String halfWidthNumber) {
        int num = Integer.parseInt(halfWidthNumber);
        return String.valueOf((char) ('０' + num));
    }
}
