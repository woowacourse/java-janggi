package janggi.infra.repository;

import java.util.Arrays;

public enum PieceType {
    BYEONG,
    CHA,
    JANG,
    JOL,
    MA,
    PO,
    SA,
    SANG,
    ;

    public static PieceType from(String string) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.name().equals(string))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 하는 기물 타입을 찾을 수 없습니다."));
    }
}
