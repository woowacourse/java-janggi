package domain.player;

import domain.common.Side;
import domain.piece.Piece;
import domain.turn.TurnState;

public class Player {
    private final Name name;
    private final Side side;
    private TurnState turnState;

    public Player(Name name, Side side, TurnState turnState) {
        this.name = name;
        this.side = side;
        this.turnState = turnState;
    }

    public void validateAlly(Piece piece) {
        if (!piece.getSide().equals(side)) {
            throw new IllegalArgumentException("상대방의 기물은 움직일 수 없습니다.");
        }
    }

    public boolean isCurrentTurn() {
        return turnState.isCurrent();
    }

    public void toggleTurn() {
        turnState = turnState.next();
    }

    public Side getSide() {
        return side;
    }

    public String getName() {
        return name.name();
    }
}
