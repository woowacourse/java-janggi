package team.janggi.domain.piece.strategy;

import java.util.ArrayList;
import java.util.List;
import team.janggi.domain.Palace;
import team.janggi.domain.Position;
import team.janggi.domain.board.BoardStateReader;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;

public class ChariotPalaceMoveStrategy extends ChariotMoveStrategy {
    public static final ChariotPalaceMoveStrategy instance = new ChariotPalaceMoveStrategy();

    @Override
    public boolean calculateMove(Position from, Position to, BoardStateReader stateReader) {
        if (super.calculateMove(from, to, stateReader)) {
            return true;
        }

        if (!isAllowDirection(from, to)) {
            return false;
        }

        final List<Piece> paths = getPath(from, to, stateReader);
        if (!isValidPath(paths)) {
            return false;
        }

        final Piece me = stateReader.getPiece(from);
        final Piece lastPiece = stateReader.getPiece(to);
        return canKill(lastPiece, me);
    }

    private boolean isValidPath(List<Piece> paths) {
        if (paths.isEmpty()) {
            return true;
        }

        return paths.stream().allMatch(piece -> piece.isPieceType(PieceType.EMPTY));
    }

    private List<Piece> getPath(Position from, Position to, BoardStateReader stateReader) {
        final List<Piece> paths = new ArrayList<>();

        int dx = Integer.signum(to.x() - from.x());
        int dy = Integer.signum(to.y() - from.y());

        int currentX = from.x() + dx;
        int currentY = from.y() + dy;

        while (currentX != to.x() && currentY != to.y()) {
            paths.add(stateReader.getPiece(new Position(currentX, currentY)));
            currentX += dx;
            currentY += dy;
        }

        return paths;
    }

    private boolean canKill(Piece target, Piece me) {
        return !target.isSameTeam(me);
    }

    private boolean isAllowDirection(Position from, Position to) {
         if (!Palace.isInPalace(from) || !Palace.isInPalace(to)) {
             return false;
         }

        final int dx = Math.abs(from.x() - to.x());
        final int dy = Math.abs(from.y() - to.y());

        return dx == dy;
    }
}
