package domain.piece;

import domain.game.Team;
import domain.position.Position;
import java.util.List;

public class EmptyPiece extends Piece {

    private EmptyPiece() {
        super(Team.NONE);
    }

    private static class LazyHolder {
        private static final EmptyPiece INSTANCE = new EmptyPiece();
    }

    public static EmptyPiece getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return false;
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        return List.of();
    }

    @Override
    public void validateRoute(List<Piece> piecesOnRoute, Piece destinationPiece) {
    }

    @Override
    public boolean isNotEmpty() {
        return false;
    }

    @Override
    public double score() {
        return 0.0;
    }
}
