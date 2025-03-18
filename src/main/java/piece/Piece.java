package piece;

import board.Position;

public class Piece {

    private final PieceType type;
    private final Position position;

    public Piece(final PieceType type, final Position position) {
        this.type = type;
        this.position = position;
    }

    public PieceType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }
}
