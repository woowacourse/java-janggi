package db.parser;

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

public enum PieceParser {
    CANNON(Cannon.class, Cannon::new),
    CHARIOT(Chariot.class, Chariot::new),
    ELEPHANT(Elephant.class, Elephant::new),
    HORSE(Horse.class, Horse::new),
    SOLDIER(Soldier.class, Soldier::new),
    GUARD(Guard.class, Guard::new),
    GENERAL(General.class, General::new);

    private final Class<? extends Piece> pieceType;
    private final Function<Side, Piece> pieceFactory;

    PieceParser(
            Class<? extends Piece> pieceType,
            Function<Side, Piece> pieceFactory
    ) {
        this.pieceType = pieceType;
        this.pieceFactory = pieceFactory;
    }

    public static Piece from(String pieceName, Side side) {
        return Arrays.stream(values())
                .filter(piece -> piece.hasSameName(pieceName))
                .map(piece -> piece.createPiece(side))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("정의되지 않은 기물입니다."));
    }

    private boolean hasSameName(String pieceName) {
        return pieceType.getSimpleName()
                .equals(pieceName);
    }

    private Piece createPiece(Side side) {
        return pieceFactory.apply(side);
    }
}
