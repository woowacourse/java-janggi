package team.janggi.domain.movestrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import team.janggi.domain.Position;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;

public class CannonMoveStrategy implements MoveStrategy {
    private static final CannonMoveStrategy INSTANCE = new CannonMoveStrategy();

    private static final List<Position> choDiagonalLeft = List.of(Position.of(3, 7), Position.of(5, 9));
    private static final List<Position> choDiagonalRight = List.of(Position.of(5, 7), Position.of(3, 9));

    private static final List<Position> hanDiagonalLeft = List.of(Position.of(3, 0), Position.of(5, 2));
    private static final List<Position> hanDiagonalRight = List.of(Position.of(5, 0), Position.of(3, 2));
    
    private CannonMoveStrategy() {
    }

    public static CannonMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean calculateMove(Position from, Position to, Map<Position, Piece> mapStatus) {
        final Piece me = mapStatus.get(from);
        final Piece lastPiece = mapStatus.get(to);

        if (!canKill(me, lastPiece)) {
            return false;
        }

        if (isDiagonalJumpDirection(from, to, mapStatus)) {
            return true;
        }

        if (!isAllowDirection(from, to)) {
            return false;
        }

        final List<Piece> paths = getPaths(from, to, mapStatus);

        return isPathsValid(paths);
    }

    private boolean isDiagonalJumpDirection(Position from, Position to, Map<Position, Piece> mapStatus) {
        if (isJumpAblePiece(mapStatus.get(Position.of(4, 8)))) {
            return containDirection(choDiagonalLeft, from, to) || containDirection(choDiagonalRight, from, to);
        }
        if (isJumpAblePiece(mapStatus.get(Position.of(4, 1)))) {
            return containDirection(hanDiagonalLeft, from, to) || containDirection(hanDiagonalRight, from, to);
        }
        return false;
    }

    private boolean containDirection(List<Position> Diagonal, Position from, Position to) {
        return Diagonal.contains(from) && Diagonal.contains(to);
    }

    private boolean canKill(Piece target, Piece me) {
        return !target.isSameTeam(me);
    }

    private boolean isPathsValid(List<Piece> paths) {
        if (paths.isEmpty()) {
            return false;
        }

        return paths.stream()
                .filter(this::isJumpAblePiece)
                .count() == 1;
    }

    private boolean isJumpAblePiece(Piece piece) {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> pieceType != PieceType.EMPTY)
                .filter(pieceType -> pieceType != PieceType.CANNON)
                .anyMatch(piece::isSamePieceType);
    }

    private List<Piece> getPaths(Position from, Position to, Map<Position, Piece> mapStatus) {
        List<Piece> paths = new ArrayList<>();

        int dx = Integer.signum(to.x() - from.x());
        int dy = Integer.signum(to.y() - from.y());

        int currentX = from.x() + dx;
        int currentY = from.y() + dy;

        while (currentX != to.x() || currentY != to.y()) {
            paths.add(mapStatus.get(new Position(currentX, currentY)));
            currentX += dx;
            currentY += dy;
        }

        return paths;
    }

    public boolean isAllowDirection(Position from, Position to) {
        int dx = to.x() - from.x();
        int dy = to.y() - from.y();

        return (dx == 0 && dy > 0) || (dx == 0 && dy < 0) || (dx > 0 && dy == 0) || (dx < 0 && dy == 0);
    }
}
