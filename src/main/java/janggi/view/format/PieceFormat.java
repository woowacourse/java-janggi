package janggi.view.format;

import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;
import java.util.Arrays;

public enum PieceFormat {

    GENERAL_CHO(PieceRule.GENERAL, Camp.CHO, "楚"),
    GENERAL_HAN(PieceRule.GENERAL, Camp.HAN, "漢"),

    SOLDIER_CHO(PieceRule.SOLDIER, Camp.CHO, "卒"),
    SOLDIER_HAN(PieceRule.SOLDIER, Camp.HAN, "兵"),

    CHARIOT_CHO(PieceRule.CHARIOT, Camp.CHO, "車"),
    CHARIOT_HAN(PieceRule.CHARIOT, Camp.HAN, "車"),

    HORSE_CHO(PieceRule.HORSE, Camp.CHO, "馬"),
    HORSE_HAN(PieceRule.HORSE, Camp.HAN, "馬"),

    CANNON_CHO(PieceRule.CANNON, Camp.CHO, "包"),
    CANNON_HAN(PieceRule.CANNON, Camp.HAN, "包"),

    GUARD_CHO(PieceRule.GUARD, Camp.CHO, "士"),
    GUARD_HAN(PieceRule.GUARD, Camp.HAN, "士"),

    ELEPHANT_CHO(PieceRule.ELEPHANT, Camp.CHO, "象"),
    ELEPHANT_HAN(PieceRule.ELEPHANT, Camp.HAN, "象"),
    ;

    private final PieceRule pieceRule;
    private final Camp camp;
    private final String format;

    PieceFormat(PieceRule pieceRule, Camp camp, String format) {
        this.pieceRule = pieceRule;
        this.camp = camp;
        this.format = format;
    }

    public static PieceFormat from(Piece piece) {
        return Arrays.stream(values())
                .filter(element -> element.match(piece))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.PIECE_FORMAT_NOT_FOUND.getMessage()));
    }

    private boolean match(Piece piece) {
        return piece.isSamePieceRule(pieceRule) && piece.isSameCamp(camp);
    }

    public String getFormat() {
        return format;
    }
}
