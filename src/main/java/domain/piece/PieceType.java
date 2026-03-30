package domain.piece;

public enum PieceType {

    GENERAL,  // 궁
    CHARIOT,  // 차
    CANNON,   // 포
    HORSE,    // 마
    ELEPHANT, // 상
    GUARD,    // 사
    SOLDIER,  // 졸
    NONE,    // null 포장
    ;

    public boolean isGeneral() {
        return this == GENERAL;
    }

}
