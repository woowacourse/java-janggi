import java.util.List;

public class Guard extends Piece{

    Guard(PieceColor color) {
        super(color);
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
    public boolean isOtherTeam(Piece other) {
        return super.isOtherTeam(other);
    }

    @Override
    public boolean canMove(int pieceCount, Piece piece) {
        return false;
    }
}
