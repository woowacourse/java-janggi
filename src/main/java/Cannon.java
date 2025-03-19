import java.util.List;

public class Cannon extends Piece
{

    Cannon(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isValidDestination(Position source, Position destination) {
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return source.getBetweenPositions(destination);
    }

    @Override
    public boolean canMove(int pieceCount, Piece piece) {
        if (pieceCount == 1 && this.isOtherTeam(piece)) {
            return true;
        }
        return false;
    }
}
