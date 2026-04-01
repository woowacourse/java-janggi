package domain.movement;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import java.util.List;

class LeapPieceRule implements MovementRule {
    @Override
    public boolean isValid(Piece piece, Paths candidatePaths, Position to, Board board) {
        for (Path path : candidatePaths.asList()) {
            if (isPathValid(piece, path, to, board)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPathValid(Piece piece, Path path, Position to, Board board) {
        if (!path.endsAt(to)) {
            return false;
        }
        List<Position> intermediates = path.intermediates();
        if (!intermediates.stream().allMatch(board::isEmpty)) {
            return false;
        }
        return board.isEmpty(to) || board.hasEnemyOf(to, piece);
    }
}