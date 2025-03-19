import java.util.List;

public class General extends Piece{

    General(PieceColor color) {
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
    public boolean canMove(Piece destinationPiece, List<Piece> piecesInRoute) {
        int pieceCountInRoute = this.countPieceInRoute(piecesInRoute);

        if(this.isOtherTeam(destinationPiece) && pieceCountInRoute == 0) {
            return true;
        }
        return false;
    }
}
