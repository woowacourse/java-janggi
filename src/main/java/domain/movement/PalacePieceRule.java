package domain.movement;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;

class PalacePieceRule implements MovementRule {
    @Override
    public boolean isValid(Piece piece, Paths candidatePaths, Position to, Board board) {
        for (Path path : candidatePaths.asList()) {
            if (isReachable(piece, path, to, board)) {
                return true;
            }
        }
        return false;
    }

    private boolean isReachable(Piece piece, Path path, Position to, Board board) {
        if (!path.endsAt(to)) {
            return false;
        }
        return board.isEmpty(to) || board.hasEnemyOf(to, piece);
    }
}
