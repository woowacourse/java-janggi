import java.awt.*;
import java.util.List;

public class EmptyPiece extends Piece {
    public EmptyPiece(Color color) {
        super(PieceColor.NONE);
    }

    @Override
    public boolean isValidDestination(Position source, Position destination) {
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return List.of();
    }

    @Override
    public boolean canMove(int pieceCount, Piece piece) {
        return false;
    }
}
