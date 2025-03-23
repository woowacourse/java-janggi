package domain.piece;

import domain.board.Position;

import java.util.List;

public class Empty extends Piece {

    private static final Empty INSTANCE = new Empty();

    public Empty() {
        super(PieceColor.NONE);
    }

    public static Empty getInstance() {
        return INSTANCE;
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
