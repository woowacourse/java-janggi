package domain.strategy;

import domain.board.Board;
import domain.board.Palace;
import domain.board.Piece;
import domain.vo.Position;

public class MoveValidator {

    public static boolean canMoveDiagonal(Position from, Position to, Board board) {
        return isValidDiagonalPoint(from, to, board) && from.isDiagonalTo(to);
    }

    public static boolean canMoveOneStepDiagonal(Position from, Position to, Board board) {
        return isValidDiagonalPoint(from, to, board) && from.isOneStepDiagonalTo(to);
    }

    private static boolean isValidDiagonalPoint(Position from, Position to, Board board) {
        Piece piece = board.findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 이동할 기물이 존재하지 않습니다."));

        Palace palace = board.getPalace(piece.getTeam());
        return palace.isDiagonalPoint(from) && palace.isDiagonalPoint(to);
    }

    private MoveValidator() {}
}
