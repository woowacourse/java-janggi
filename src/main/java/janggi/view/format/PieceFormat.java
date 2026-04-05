package janggi.view.format;

import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceStrategy;
import janggi.exception.ExceptionMessage;
import java.util.Arrays;

public enum PieceFormat {

    GENERAL_CHO(PieceStrategy.GENERAL, Camp.CHO, "楚"),
    GENERAL_HAN(PieceStrategy.GENERAL, Camp.HAN, "漢"),

    SOLDIER_CHO(PieceStrategy.SOLDIER, Camp.CHO, "卒"),
    SOLDIER_HAN(PieceStrategy.SOLDIER, Camp.HAN, "兵"),

    CHARIOT_CHO(PieceStrategy.CHARIOT, Camp.CHO, "車"),
    CHARIOT_HAN(PieceStrategy.CHARIOT, Camp.HAN, "車"),

    HORSE_CHO(PieceStrategy.HORSE, Camp.CHO, "馬"),
    HORSE_HAN(PieceStrategy.HORSE, Camp.HAN, "馬"),

    CANNON_CHO(PieceStrategy.CANNON, Camp.CHO, "包"),
    CANNON_HAN(PieceStrategy.CANNON, Camp.HAN, "包"),

    GUARD_CHO(PieceStrategy.GUARD, Camp.CHO, "士"),
    GUARD_HAN(PieceStrategy.GUARD, Camp.HAN, "士"),

    ELEPHANT_CHO(PieceStrategy.ELEPHANT, Camp.CHO, "象"),
    ELEPHANT_HAN(PieceStrategy.ELEPHANT, Camp.HAN, "象"),
    ;

    private final PieceStrategy pieceStrategy;
    private final Camp camp;
    private final String format;

    PieceFormat(PieceStrategy pieceStrategy, Camp camp, String format) {
        this.pieceStrategy = pieceStrategy;
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
        return piece.isSamePieceRule(pieceStrategy) && piece.isSameCamp(camp);
    }

    public String getFormat() {
        return format;
    }
}
