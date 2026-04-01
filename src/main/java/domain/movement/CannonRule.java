package domain.movement;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import java.util.List;
import java.util.Optional;

class CannonRule implements MovementRule {
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
        if (!path.contains(to)) {
            return false;
        }
        List<Position> intermediates = path.subPathTo(to).intermediates();
        if (!hasExactlyOneNonCannon(intermediates, board)) {
            return false;
        }
        return isValidDestination(piece, to, board);
    }

    private boolean hasExactlyOneNonCannon(List<Position> intermediates, Board board) {
        long nonCannons = intermediates.stream()
                .filter(board::hasAnyPiece)
                .filter(pos -> isNonCannonAt(pos, board))
                .count();
        long cannons = intermediates.stream()
                .filter(board::hasAnyPiece)
                .filter(pos -> isCannonAt(pos, board))
                .count();
        return nonCannons == 1 && cannons == 0;
    }

    private boolean isNonCannonAt(Position position, Board board) {
        return board.pieceAt(position).map(piece -> !piece.isCannon()).orElse(false);
    }

    private boolean isCannonAt(Position position, Board board) {
        return board.pieceAt(position).map(Piece::isCannon).orElse(false);
    }

    private boolean isValidDestination(Piece piece, Position to, Board board) {
        if (board.isEmpty(to)) {
            return true;
        }
        if (!board.hasEnemyOf(to, piece)) {
            return false;
        }
        Optional<Piece> target = board.pieceAt(to);
        return !target.map(Piece::isCannon).orElse(false);
    }
}