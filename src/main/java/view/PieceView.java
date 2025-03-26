package view;

import domain.piece.Piece;
import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.Elephant;
import domain.piece.category.Guard;
import domain.piece.category.Horse;
import domain.piece.category.King;
import domain.piece.category.Soldier;
import java.util.Arrays;

public enum PieceView {
    KING(King.class, "K"),
    CANNON(Cannon.class, "c"),
    CHARIOT(Chariot.class, "r"),
    ELEPHANT(Elephant.class, "e"),
    GUARD(Guard.class, "a"),
    HORSE(Horse.class, "h"),
    SOLDIER(Soldier.class, "p"),
    ;

    private final Class<?> aClass;
    private final String name;

    PieceView(final Class<?> aClass, final String name) {
        this.aClass = aClass;
        this.name = name;
    }

    public static String findNameByClass(final Piece piece) {
        PieceView pieceView = Arrays.stream(values())
                .filter(element -> element.aClass == piece.getClass())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("표기할 수 없는 기물입니다."));

        return pieceView.name;
    }
}
