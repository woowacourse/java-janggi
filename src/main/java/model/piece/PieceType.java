package model.piece;

import java.util.Arrays;

public enum PieceType {
    PALACE(King::new, 0),
    CHARIOT(Chariot::new, 13),
    PAO(Pao::new, 7),
    HORSE(Horse::new, 5),
    ELEPHANT(Elephant::new, 3),
    SOLDIER(Soldier::new, 3),
    PAWN(Pawn::new, 2),
    ;

    private final PieceConstructor constructor;
    private final int score;

    PieceType(PieceConstructor constructor, int score) {
        this.constructor = constructor;
        this.score = score;
    }

    public static PieceType from(String name) {
        return Arrays.stream(values())
            .filter(pieceType -> pieceType.name().equals(name))
            .findAny()
            .orElseThrow(() -> new IllegalStateException("[ERROR] 존재하지 않는 기물 종류입니다."));
    }

    public PieceConstructor getConstructor() {
        return constructor;
    }

    public int getScore() {
        return score;
    }
}
