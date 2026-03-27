package janggi.domain.game;

import janggi.domain.piece.Piece;
import java.util.function.BiFunction;

public class Player {

    private final String name;
    private final Side side;

    public Player(String name, Side side) {
        this.name = name;
        this.side = side;
    }

    public boolean isMyTurn(Turn turn) {
        return turn.isCurrent(this.side);
    }

    public boolean isOwnPiece(Piece piece) {
        return piece.isBelongTo(side);
    }

    public <T> T map(BiFunction<String, Side, T> mapper) {
        return mapper.apply(this.name, this.side);
    }
}
