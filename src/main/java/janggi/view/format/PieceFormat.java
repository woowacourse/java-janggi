package janggi.view.format;

import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.Arrays;

public enum PieceFormat {

    GENERAL_CHO("楚", PieceType.GENERAL, Camp.CHO),
    GENERAL_HAN("漢", PieceType.GENERAL, Camp.HAN),
    SOLDIER_CHO("卒", PieceType.SOLDIER, Camp.CHO),
    SOLDIER_HAN("兵", PieceType.SOLDIER, Camp.HAN),
    CHARIOT_CHO("車", PieceType.CHARIOT, Camp.CHO),
    CHARIOT_HAN("車", PieceType.CHARIOT, Camp.HAN),
    HORSE_CHO("馬", PieceType.HORSE, Camp.CHO),
    HORSE_HAN("馬", PieceType.HORSE, Camp.HAN),
    CANNON_CHO("包", PieceType.CANNON, Camp.CHO),
    CANNON_HAN("包", PieceType.CANNON, Camp.HAN),
    GUARD_CHO("士", PieceType.GUARD, Camp.CHO),
    GUARD_HAN("士", PieceType.GUARD, Camp.HAN),
    ELEPHANT_CHO("象", PieceType.ELEPHANT, Camp.CHO),
    ELEPHANT_HAN("象", PieceType.ELEPHANT, Camp.HAN),
    ;

    private final String symbol;
    private final Piece piece;

    PieceFormat(String symbol, PieceType pieceType, Camp camp) {
        this.symbol = symbol;
        this.piece = new Piece(camp, pieceType);
    }

    public static PieceFormat from(Piece piece) {
        return Arrays.stream(values())
                .filter(pieceFormat -> pieceFormat.piece.equals(piece))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 기물입니다."));
    }

    public String symbol() {
        return symbol;
    }
}
