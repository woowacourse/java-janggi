package janggi.domain.piece;

import janggi.domain.board.Position;
import java.util.List;

public class GuardPiece extends Piece {
    public GuardPiece(Team team) {
        super(team, Name.GUARD);
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
