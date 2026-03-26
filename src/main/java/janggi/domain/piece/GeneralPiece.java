package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.board.Position;
import java.util.List;

public class GeneralPiece extends Piece {

    public GeneralPiece(Team team) {
        super(team, Name.GENERAL);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int preX = from.getX();
        int preY = from.getY();

        int nextX = to.getX();
        int nextY = to.getY();

        if (abs(preX - nextX) > 1) {
            return false;
        }
        if (abs(preY - nextY) > 1) {
            return false;
        }

        return abs(preX - nextX) + abs(preY - nextY) <= 1;
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of(to);
    }
}
