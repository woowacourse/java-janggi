package piece;

import strategy.MoveStrategy;

public class Piece {

    private final Position position;
    private final MoveStrategy moveStrategy;

    public Piece(Position position, MoveStrategy moveStrategy) {
        this.position = position;
        this.moveStrategy = moveStrategy;
    }
}
