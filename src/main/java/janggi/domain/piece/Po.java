package janggi.domain.piece;

import janggi.domain.piece.direction.FourDirection;
import janggi.domain.piece.direction.GungDirection;
import janggi.domain.setting.CampType;
import janggi.domain.value.JanggiPosition;
import java.util.List;

public class Po extends Piece {

    private Po(final JanggiPosition janggiPosition) {
        super(PieceType.PO, janggiPosition);
    }

    public static Po from(final JanggiPosition janggiPosition) {
        return new Po(janggiPosition);
    }

    public static List<Po> generateInitialPos(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.PO.getHeight());
        return PieceType.PO.getDefaultXPositions().stream()
                .map(xPosition -> new Po(new JanggiPosition(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Po move(final JanggiPosition destination, final Pieces enemy, final Pieces allies) {
        if (!ableToMove(destination, enemy, allies)) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        enemy.beAttackedAt(destination);
        return new Po(destination);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, Pieces enemy, Pieces allies) {
        if (janggiPosition.isDiagonalPositionInCastle() && destination.isDiagonalPositionInCastle()) {
            List<JanggiPosition> gungPathPositions = GungDirection.of(janggiPosition, destination);
            return isValidMoveInCastle(gungPathPositions, enemy, allies);
        }
        if (!isValidMove(destination)) {
            return false;
        }
        if (!allies.isNotBlockedBy(destination)) {
            return false;
        }
        List<JanggiPosition> pathPositions = FourDirection.from(destination, janggiPosition);
        List<Piece> alliesInPath = allies.searchPiecesInPath(pathPositions);
        List<Piece> enemyInPath = enemy.searchPiecesInPath(pathPositions);

        if (isPoInPath(alliesInPath) || isPoInPath(enemyInPath)) {
            return false;
        }
        if (enemy.isNotBlockedBy(destination) && alliesInPath.size() + enemyInPath.size() > 1) {
            return false;
        }
        if (!enemy.isNotBlockedBy(destination) && alliesInPath.size() + enemyInPath.size() > 2) {
            return false;
        }
        return alliesInPath.size() + enemyInPath.size() == 1 || alliesInPath.size() + enemyInPath.size() == 2;
    }

    private boolean isValidMoveInCastle(List<JanggiPosition> pathPositions, Pieces enemy, Pieces allies) {
        if (pathPositions.isEmpty()) {
            return false;
        }

        JanggiPosition start = pathPositions.getFirst();
        JanggiPosition end = pathPositions.getLast();

        int dx = Math.abs(end.x() - start.x());
        int dy = Math.abs(end.y() - start.y());

        return isValidStraightMove(dx, dy, start, end, enemy, allies) || isValidDiagonalMove(dx, dy, start, end, enemy, allies);
    }

    private boolean isValidStraightMove(int dx, int dy, JanggiPosition start, JanggiPosition end,
                                        Pieces enemy, Pieces allies) {
        boolean isHorizontalMove = dx == 1 && dy == 0;
        boolean isVerticalMove = dx == 0 && dy == 1;

        if (!(isHorizontalMove || isVerticalMove)) {
            return false;
        }

        boolean isStartNotBlocked = enemy.isNotBlockedBy(start) || allies.isNotBlockedBy(start);
        boolean isEndNotBlockedByEnemy = enemy.isNotBlockedBy(end);

        return isStartNotBlocked && isEndNotBlockedByEnemy;
    }

    private boolean isValidDiagonalMove(int dx, int dy, JanggiPosition start, JanggiPosition end,
                                        Pieces enemy, Pieces allies) {
        if (!(dx == 1 && dy == 1)) {
            return false;
        }

        boolean isStartNotBlocked = enemy.isNotBlockedBy(start) || allies.isNotBlockedBy(start);
        boolean isEndNotBlockedByEnemy = enemy.isNotBlockedBy(end);

        return isStartNotBlocked && isEndNotBlockedByEnemy;
    }

    private boolean isValidMove(JanggiPosition destination) {
        return janggiPosition.x() == destination.x() || janggiPosition.y() == destination.y();
    }

    private boolean isPoInPath(List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> piece.pieceType == pieceType);
    }
}
