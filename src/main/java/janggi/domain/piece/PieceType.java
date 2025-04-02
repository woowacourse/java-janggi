package janggi.domain.piece;

import janggi.domain.Turn;
import janggi.domain.piece.limit.*;
import janggi.domain.piece.unlimit.Cannon;
import janggi.domain.piece.unlimit.Chariot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum PieceType {
    KING("G", 0, King::new),
    GUARD("S", 3, Guard::new),
    HORSE("M", 5, Horse::new),
    ELEPHANT("E", 3, Elephant::new),
    CANNON("P", 7, Cannon::new),
    CHARIOT("C", 13, Chariot::new),
    SOLDIER("J", 2, Soldier::new),
    EMPTY("·", 0, Empty::new);

    private final String symbol;
    private final int score;
    private final Function<Turn, Piece> constructor;

    PieceType(final String symbol, final int score, final Function<Turn, Piece> constructor) {
        this.symbol = symbol;
        this.score = score;
        this.constructor = constructor;
    }

    public static Piece createPiece(final String symbol, final Turn side) {
        PieceType pieceType = findPieceTypeBySymbol(symbol);
        return pieceType.constructor.apply(side);
    }

    public static PieceType findPieceTypeBySymbol(final String symbol) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.getSymbol().equals(symbol))
                .findFirst()
                .orElse(EMPTY);
    }

    public static List<PieceType> valuesNotEmpty() {
        List<PieceType> pieceTypes = new ArrayList<>(List.of(values()));
        pieceTypes.remove(EMPTY);
        return pieceTypes;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getScore() { return score; }
}
