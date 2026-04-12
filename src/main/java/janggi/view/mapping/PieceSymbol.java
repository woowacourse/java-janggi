package janggi.view.mapping;

import janggi.model.piece.PieceType;
import java.util.Arrays;

public enum PieceSymbol {
    MA("마", PieceType.MA),
    SANG("상", PieceType.SANG),
    CHA("차", PieceType.CHA),
    PHO("포", PieceType.PHO),
    JANG("장", PieceType.JANG),
    SA("사", PieceType.SA),
    BYEONG("병", PieceType.BYEONG);

    private final String symbol;
    private final PieceType matchingType;

    PieceSymbol(String symbol, PieceType matchingType) {
        this.symbol = symbol;
        this.matchingType = matchingType;
    }

    public static PieceSymbol from(PieceType type) {
        return Arrays.stream(values())
                .filter(value -> value.getMatchingType() == type)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 타입입니다."));
    }

    public String getSymbol() {
        return symbol;
    }

    public PieceType getMatchingType() {
        return matchingType;
    }
}
