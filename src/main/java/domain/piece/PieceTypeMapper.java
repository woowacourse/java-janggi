package domain.piece;

import domain.Side;

import java.util.function.Function;

public enum PieceTypeMapper {

    KING(PieceType.KING, King::new),
    GUARD(PieceType.GUARD, Guard::new),
    CHARIOT(PieceType.CHARIOT, Chariot::new),
    HORSE(PieceType.HORSE, Horse::new),
    ELEPHANT(PieceType.ELEPHANT, Elephant::new),
    CANNON(PieceType.CANNON, Cannon::new),
    PAWN(PieceType.PAWN, Pawn::new),
    EMPTY(PieceType.EMPTY, side -> EmptyPiece.getInstance());

    private final PieceType pieceType;
    private final Function<Side, Piece> constructor;

    PieceTypeMapper(PieceType pieceType, Function<Side, Piece> constructor) {
        this.pieceType = pieceType;
        this.constructor = constructor;
    }

    public static Piece create(PieceType type, Side side) {
        for (PieceTypeMapper mapper : values()) {
            if (mapper.pieceType == type) {
                return mapper.constructor.apply(side);
            }
        }
        throw new IllegalArgumentException("Unknown piece type: " + type);
    }
}