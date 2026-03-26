package janggi.domain.game;

import janggi.domain.piece.PieceVO;
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

    public boolean isOwnPiece(PieceVO pieceVo) {
        return side.isSameSide(pieceVo.side());
    }
}
