package domain.movement;

import domain.game.Board;
import domain.game.Piece;
import domain.game.Position;
import java.util.List;

public final class MovementValidator {
    private final Board board;

    public MovementValidator(Board board) {
        this.board = board;
    }

    public boolean isValid(Piece piece, Paths candidatePaths, Position to) {
        if (board.hasFriend(to, piece.getTeam())) {
            return false;
        }

        return switch (piece.getPieceType()) {
            case CHARIOT -> isValidChariot(piece, candidatePaths, to);
            case CANNON -> isValidCannon(piece, candidatePaths, to);
            case HORSE, ELEPHANT -> isValidStepPiece(piece, candidatePaths, to);
            case GENERAL, GUARD -> isValidPalacePiece(piece, candidatePaths, to);
            case SOLDIER -> isValidPalacePiece(piece, candidatePaths, to);
        };
    }

    private boolean isValidChariot(Piece piece, Paths candidatePaths, Position to) {
        for (Path path : candidatePaths.asList()) {
            if (!path.contains(to)) {
                continue;
            }
            Path subPath = path.subPathTo(to);
            List<Position> intermediates = subPath.intermediates();

            boolean allIntermediatesEmpty = intermediates.stream()
                    .allMatch(board::isEmpty);

            if (!allIntermediatesEmpty) {
                continue;
            }

            if (board.isEmpty(to) || board.hasEnemy(to, piece.getTeam())) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidCannon(Piece piece, Paths candidatePaths, Position to) {
        for (Path path : candidatePaths.asList()) {
            if (!path.contains(to)) {
                continue;
            }
            Path subPath = path.subPathTo(to);
            List<Position> intermediates = subPath.intermediates();

            long nonCannonCount = intermediates.stream()
                    .filter(board::hasAnyPiece)
                    .filter(pos -> board.pieceAt(pos).map(p -> !p.isCannon()).orElse(false))
                    .count();

            long cannonCount = intermediates.stream()
                    .filter(board::hasAnyPiece)
                    .filter(pos -> board.pieceAt(pos).map(Piece::isCannon).orElse(false))
                    .count();

            if (nonCannonCount != 1 || cannonCount != 0) {
                continue;
            }

            if (board.isEmpty(to)) {
                return true;
            }

            if (board.hasEnemy(to, piece.getTeam())) {
                boolean destIsCannon = board.pieceAt(to).map(Piece::isCannon).orElse(false);
                if (!destIsCannon) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidStepPiece(Piece piece, Paths candidatePaths, Position to) {
        for (Path path : candidatePaths.asList()) {
            if (!path.endsAt(to)) {
                continue;
            }

            if (!path.intermediates().stream()
                    .allMatch(board::isEmpty)) {
                continue;
            }

            if (board.isEmpty(to) || board.hasEnemy(to, piece.getTeam())) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidPalacePiece(Piece piece, Paths candidatePaths, Position to) {
        for (Path path : candidatePaths.asList()) {
            if (!path.endsAt(to)) {
                continue;
            }

            if (board.isEmpty(to) || board.hasEnemy(to, piece.getTeam())) {
                return true;
            }
        }
        return false;
    }
}