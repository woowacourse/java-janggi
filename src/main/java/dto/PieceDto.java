package dto;

import java.util.Arrays;

import model.piece.Chariot;
import model.piece.Elephant;
import model.piece.Horse;
import model.piece.Palace;
import model.piece.Pao;
import model.piece.Pawn;
import model.piece.Piece;
import model.piece.Soldier;

public enum PieceDto {
    PALACE("궁", Palace.class),
    SOLDIER("사", Soldier.class),
    ELEPHANT("상", Elephant.class),
    HORSE("마", Horse.class),
    CHARIOT("차", Chariot.class),
    PAO("포", Pao.class),
    PAWN("병", Pawn.class),
    ;

    private final String name;
    private final Class<? extends Piece> pieceClassType;

    PieceDto(String name, Class<? extends Piece> pieceClassType) {
        this.name = name;
        this.pieceClassType = pieceClassType;
    }

    public static PieceDto from(Piece piece) {
        return Arrays.stream(values())
            .filter(pieceDto -> pieceDto.pieceClassType.isInstance(piece))
            .findAny()
            .orElseThrow(() -> new IllegalStateException("[ERROR] 잘못된 기물 종류입니다."));
    }

    public String getName() {
        return name;
    }
}
