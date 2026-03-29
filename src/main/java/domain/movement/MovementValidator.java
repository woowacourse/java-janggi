package domain.movement;

import domain.game.Board;
import domain.game.Piece;
import domain.game.Position;
import java.util.List;
import java.util.Optional;

public final class MovementValidator {
    private final Board board;

    public MovementValidator(Board board) {
        this.board = board;
    }

    public boolean isValid(Piece piece, Paths candidatePaths, Position to) {
        if (board.hasFriendOf(to, piece)) {
            return false;
        }
        return isValidForType(piece, candidatePaths, to);
    }

    private boolean isValidForType(Piece piece, Paths candidatePaths, Position to) {
        if (piece.isChariot()) {
            return isValidChariot(piece, candidatePaths, to);
        }
        if (piece.isCannon()) {
            return isValidCannon(piece, candidatePaths, to);
        }
        if (piece.isStepPiece()) {
            return isValidStepPiece(piece, candidatePaths, to);
        }
        return isValidPalacePiece(piece, candidatePaths, to);
    }

    private boolean isValidChariot(Piece piece, Paths candidatePaths, Position to) {
        for (Path path : candidatePaths.asList()) {
            if (isChariotPathValid(piece, path, to)) {
                return true;
            }
        }
        return false;
    }

    private boolean isChariotPathValid(Piece piece, Path path, Position to) {
        if (!path.contains(to)) {
            return false;
        }
        if (!isPathClearTo(path, to)) {
            return false;
        }
        return board.isEmpty(to) || board.hasEnemyOf(to, piece);
    }

    private boolean isPathClearTo(Path path, Position to) {
        Path subPath = path.subPathTo(to);
        List<Position> intermediates = subPath.intermediates();
        return intermediates.stream().allMatch(board::isEmpty);
    }

    private boolean isValidCannon(Piece piece, Paths candidatePaths, Position to) {
        for (Path path : candidatePaths.asList()) {
            if (isCannonPathValid(piece, path, to)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCannonPathValid(Piece piece, Path path, Position to) {
        if (!path.contains(to)) {
            return false;
        }
        Path subPath = path.subPathTo(to);
        List<Position> intermediates = subPath.intermediates();
        if (!hasExactlyOneNonCannon(intermediates)) {
            return false;
        }
        return isValidCannonDestination(piece, to);
    }

    private boolean hasExactlyOneNonCannon(List<Position> intermediates) {
        return countNonCannons(intermediates) == 1 && countCannons(intermediates) == 0;
    }

    private long countNonCannons(List<Position> intermediates) {
        return intermediates.stream()
                .filter(board::hasAnyPiece)
                .filter(this::isNonCannonAt)
                .count();
    }

    private boolean isNonCannonAt(Position position) {
        Optional<Piece> foundPiece = board.pieceAt(position);
        Optional<Boolean> isNotCannon = foundPiece.map(piece -> !piece.isCannon());
        return isNotCannon.orElse(false);
    }

    private long countCannons(List<Position> intermediates) {
        return intermediates.stream()
                .filter(board::hasAnyPiece)
                .filter(this::isCannonAt)
                .count();
    }

    private boolean isCannonAt(Position position) {
        Optional<Piece> foundPiece = board.pieceAt(position);
        Optional<Boolean> isCannon = foundPiece.map(Piece::isCannon);
        return isCannon.orElse(false);
    }

    private boolean isValidCannonDestination(Piece piece, Position to) {
        if (board.isEmpty(to)) {
            return true;
        }
        if (!board.hasEnemyOf(to, piece)) {
            return false;
        }
        Optional<Piece> target = board.pieceAt(to);
        Optional<Boolean> isCannon = target.map(Piece::isCannon);
        return !isCannon.orElse(false);
    }

    private boolean isValidStepPiece(Piece piece, Paths candidatePaths, Position to) {
        for (Path path : candidatePaths.asList()) {
            if (isStepPathValid(piece, path, to)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStepPathValid(Piece piece, Path path, Position to) {
        if (!path.endsAt(to)) {
            return false;
        }
        List<Position> intermediates = path.intermediates();
        if (!intermediates.stream().allMatch(board::isEmpty)) {
            return false;
        }
        return board.isEmpty(to) || board.hasEnemyOf(to, piece);
    }

    private boolean isValidPalacePiece(Piece piece, Paths candidatePaths, Position to) {
        for (Path path : candidatePaths.asList()) {
            if (isReachable(piece, path, to)) {
                return true;
            }
        }
        return false;
    }

    private boolean isReachable(Piece piece, Path path, Position to) {
        if (!path.endsAt(to)) {
            return false;
        }
        return board.isEmpty(to) || board.hasEnemyOf(to, piece);
    }
}