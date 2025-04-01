package janggi.domain.piece;

import java.util.function.Function;

public enum PieceType {
    CANNON(Cannon::new),
    CHARIOT(Chariot::new),
    SOLDIER(Soldier::from),
    ELEPHANT(Elephant::new),
    GENERAL(General::new),
    GUARD(Guard::new),
    HORSE(Horse::new),
    EMPTY(Elephant::new);

    private final Function<Dynasty, Piece> createPieceFunction;

    PieceType(Function<Dynasty, Piece> createPieceFunction) {
        this.createPieceFunction = createPieceFunction;
    }

    public Piece createPiece(Dynasty dynasty) {
        return createPieceFunction.apply(dynasty);
    }
}
