package db.converter;

import domain.game.Side;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Soldier;
import java.util.Arrays;
import java.util.function.Function;

public enum PieceConverter {
    CANNON(Cannon.class, Cannon::new),
    CHARIOT(Chariot.class, Chariot::new),
    ELEPHANT(Elephant.class, Elephant::new),
    HORSE(Horse.class, Horse::new),
    SOLDIER(Soldier.class, Soldier::new),
    GUARD(Guard.class, Guard::new),
    GENERAL(General.class, General::new);

    private static final String NOT_DEFINED_PIECE = "정의되지 않은 기물입니다.";

    private final Class<? extends Piece> pieceType;
    private final Function<Side, Piece> pieceFactory;

    PieceConverter(
            Class<? extends Piece> pieceType,
            Function<Side, Piece> pieceFactory
    ) {
        this.pieceType = pieceType;
        this.pieceFactory = pieceFactory;
    }

    public static Piece toDomain(String pieceName, Side side) {
        return Arrays.stream(values())
                .filter(piece -> piece.hasSameName(pieceName))
                .map(piece -> piece.createPiece(side))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(NOT_DEFINED_PIECE));
    }

    public static String toColumnValue(Piece piece) {
        return Arrays.stream(values())
                .filter(pieceConverter -> pieceConverter.hasSameType(piece))
                .map(PieceConverter::getName)
                .findAny()
                .orElseThrow(() -> new IllegalStateException(NOT_DEFINED_PIECE));
    }

    private boolean hasSameName(String pieceName) {
        return pieceType.getSimpleName()
                .equals(pieceName);
    }

    private boolean hasSameType(Piece piece) {
        return pieceType.equals(piece.getClass());
    }

    private String getName() {
        return pieceType.getSimpleName();
    }

    private Piece createPiece(Side side) {
        return pieceFactory.apply(side);
    }
}
