package janggi.view.format;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.camp.CampType;
import janggi.exception.ExceptionMessage;
import java.util.Arrays;

public enum PieceFormat {

    GENERAL_CHO(PieceRule.GENERAL, CampType.CHO, "楚"),
    GENERAL_HAN(PieceRule.GENERAL, CampType.HAN, "漢"),

    SOLDIER_CHO(PieceRule.SOLDIER, CampType.CHO, "卒"),
    SOLDIER_HAN(PieceRule.SOLDIER, CampType.HAN, "兵"),

    CHARIOT_CHO(PieceRule.CHARIOT, CampType.CHO, "車"),
    CHARIOT_HAN(PieceRule.CHARIOT, CampType.HAN, "車"),

    HORSE_CHO(PieceRule.HORSE, CampType.CHO, "馬"),
    HORSE_HAN(PieceRule.HORSE, CampType.HAN, "馬"),

    CANNON_CHO(PieceRule.CANNON, CampType.CHO, "包"),
    CANNON_HAN(PieceRule.CANNON, CampType.HAN, "包"),

    GUARD_CHO(PieceRule.GUARD, CampType.CHO, "士"),
    GUARD_HAN(PieceRule.GUARD, CampType.HAN, "士"),

    ELEPHANT_CHO(PieceRule.ELEPHANT, CampType.CHO, "象"),
    ELEPHANT_HAN(PieceRule.ELEPHANT, CampType.HAN, "象"),
    ;

    private final PieceRule pieceRule;
    private final CampType campType;
    private final String format;

    PieceFormat(PieceRule pieceRule, CampType campType, String format) {
        this.pieceRule = pieceRule;
        this.campType = campType;
        this.format = format;
    }

    public static PieceFormat from(Piece piece) {
        return Arrays.stream(values())
                .filter(element -> element.match(piece))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.PIECE_FORMAT_NOT_FOUND.getMessage()));
    }

    private boolean match(Piece piece) {
        return piece.isSamePieceRule(pieceRule) && piece.isSameCampType(campType);
    }

    public String getFormat() {
        return format;
    }
}
