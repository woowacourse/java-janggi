package domain.piece;

import domain.board.Position;

import java.util.List;

public class Empty extends Piece {
    public Empty() {
        super(PieceColor.NONE, PieceType.NONE);
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
