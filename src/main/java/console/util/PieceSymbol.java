package console.util;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import java.util.Arrays;

public enum PieceSymbol {
    PALACE("궁", PieceType.PALACE),
    SOLDIER("사", PieceType.SOLDIER),
    ELEPHANT("상", PieceType.ELEPHANT),
    HORSE("마", PieceType.HORSE),
    CHARIOT("차", PieceType.CHARIOT),
    PAO("포", PieceType.CANNON),
    PAWN("병", PieceType.PAWN),
    EMPTY("＿", PieceType.BLANK);

    private final String name;
    private final PieceType type;

    PieceSymbol(String name, PieceType type) {
        this.name = name;
        this.type = type;
    }

    public static String from(Piece piece) {
        return Arrays.stream(values())
                .filter(symbol -> symbol.type == piece.type())
                .findAny()
                .orElse(EMPTY)
                .name;
    }

    public String getName() {
        return name;
    }
}
