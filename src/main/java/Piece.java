import java.util.List;

public abstract class Piece {
    final PieceColor color;

    Piece(PieceColor color) {
        this.color = color;
    }

    public abstract boolean canMove(Position source, Position destination);

    public abstract List<Position> findAllRoute(Position source, Position destination);
}
