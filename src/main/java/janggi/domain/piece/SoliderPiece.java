package janggi.domain.piece;

import janggi.domain.board.Position;
import java.util.List;

public class SoliderPiece extends Piece {
    public SoliderPiece(Team team) {
        super(team, Name.SOLDIER);
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
