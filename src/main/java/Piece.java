import java.util.List;

public abstract class Piece {
    final PieceColor color;

    Piece(PieceColor color) {
        this.color = color;
    }

    public boolean isOtherTeam(Piece other) {
        return this.color != other.color;
    }

    public abstract boolean isValidDestination(Position source, Position destination);

    public abstract List<Position> findAllRoute(Position source, Position destination);

    public abstract boolean canMove(int pieceCount, Piece piece);
}
