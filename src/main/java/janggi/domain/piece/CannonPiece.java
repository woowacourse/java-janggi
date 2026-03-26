package janggi.domain.piece;

import janggi.domain.board.Position;
import java.util.List;

public class CannonPiece extends Piece {
    public CannonPiece(Team team) {
        super(team, Name.CANNON);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        return false;
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of();
    }
}
