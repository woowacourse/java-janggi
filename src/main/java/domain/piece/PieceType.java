package domain.piece;

public enum PieceType {

    GENERAL,  // 궁
    CHARIOT,  // 차
    CANNON,   // 포
    HORSE,    // 마
    ELEPHANT, // 상
    GUARD,    // 사
    SOLDIER,  // 졸
    NONE,
    ;

    public boolean isGeneral() {
        return this == GENERAL;
    }

    public boolean isCannon() {
        return this == CANNON;
    }

    public boolean isLinearPiece() {
        return this == CANNON || this == CHARIOT;
    }

}
