package janggi.domain.piece;

import janggi.domain.board.Position;
import java.util.List;
import java.util.Map;

public class SoliderPiece extends Piece {
    public SoliderPiece(Team team) {
        super(team, Name.SOLDIER);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int preX = from.getX();
        int preY = from.getY();

        int nextY = to.getY();
        int nextX = to.getX();

        if (isHan()) {
            if (nextY - preY == 1 && preX == nextX) {
                return true;
            }
            return (Math.abs(nextX - preX) == 1) && (nextY == preY);
        }
        if (preY - nextY == 1 && preX == nextX) {
            return true;
        }
        return (Math.abs(nextX - preX) == 1) && (nextY == preY);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of(to);
    }

    @Override
    public boolean determineMovingRule(Map<Position, Piece> positionPieces, Position to) {
        for (Piece piece : positionPieces.values()) {
            if (piece.isSameTeam(this)) {
                return false;
            }
        }
        return true;
    }
}
