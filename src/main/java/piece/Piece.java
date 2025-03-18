package piece;

import move.MoveStrategy;
import move.Position;

public abstract class Piece {
    private final MoveStrategy moveStrategy;
    private Position position;

    public Piece(MoveStrategy moveStrategy, Position position) {
        this.moveStrategy = moveStrategy;
        this.position = position;
    }

    public void move(Position to) {
        this.position = moveStrategy.move(position, to);
    }

    public Position position() {
        return position;
    }
}
