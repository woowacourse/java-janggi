package janggi;

import janggi.piece.Piece;
import java.util.function.Predicate;

public enum PieceType {
    GENERAL(Piece::isGeneral),
    GUARD(Piece::isGuard),
    HORSE(Piece::isHorse),
    ELEPHANT(Piece::isElephant),
    CANON(Piece::isCanon),
    CHARIOT(Piece::isChariot),
    SOLDIER(Piece::isSoldier);

    private final Predicate<Piece> typeCondition;

    PieceType(Predicate<Piece> typeCondition) {
        this.typeCondition = typeCondition;
    }

    public static PieceType from(Piece piece) {
        for (PieceType pieceType : values()) {
            Predicate<Piece> condition = pieceType.typeCondition;
            if (condition.test(piece)) {
                return pieceType;
            }
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 타입의 기물입니다.");
    }
}
