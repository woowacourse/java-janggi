import java.awt.*;
import java.util.List;

public class EmptyPiece extends Piece {
    public EmptyPiece() {
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
    public boolean canMove(Piece piece, List<Piece> piecesInRoute) {
        return false;
    }
}
