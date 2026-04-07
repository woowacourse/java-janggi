package domain.strategy;

import domain.board.Board;
import domain.vo.Position;

public class MoveValidator {

    public static boolean canMoveDiagonal(Position from, Position to, Board board) {
        return board.canMoveDiagonallyInPalace(from, to)
                && from.isDiagonalTo(to);
    }

    public static boolean canMoveOneStepDiagonal(Position from, Position to, Board board) {
        return board.canMoveDiagonallyInPalace(from, to)
                && from.isOneStepDiagonalTo(to);
    }

    private MoveValidator() {}
}
