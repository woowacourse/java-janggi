package janggi.domain.piece;

import janggi.domain.board.Position;
import java.util.List;

public class HorsePiece extends Piece {
    public HorsePiece(Team team) {
        super(team, Name.HORSE);
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
