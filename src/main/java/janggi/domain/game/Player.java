package janggi.domain.game;

import janggi.domain.piece.Piece;
import janggi.dto.PlayerDTO;

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

    public PlayerDTO mapToVO() {
        return new PlayerDTO(name, side);
    }

    public boolean isOwnPiece(Piece Piece) {
        return side.isSameSide(Piece.side());
    }
}
