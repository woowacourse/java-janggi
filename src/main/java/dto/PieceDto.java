package dto;

import java.util.Arrays;

import piece.Piece;
import piece.PieceType;

public enum PieceDto {
    PALACE("궁", PieceType.PALACE),
    SOLDIER("사", PieceType.SOLDIER),
    ELEPHANT("상", PieceType.ELEPHANT),
    HORSE("마", PieceType.HORSE),
    CHARIOT("차", PieceType.CHARIOT),
    PAO("포", PieceType.PAO),
    PAWN("병", PieceType.PAWN),
    ;

    private final String name;
    private final PieceType type;

    PieceDto(String name, PieceType type) {
        this.name = name;
        this.type = type;
    }

    public static PieceDto from(Piece piece) {
        return Arrays.stream(values())
            .filter(pieceDto -> pieceDto.type == piece.type())
            .findAny()
            .orElseThrow(() -> new IllegalStateException("[ERROR] 잘못된 기물 종류입니다."));
    }

    public String getName() {
        return name;
    }
}
