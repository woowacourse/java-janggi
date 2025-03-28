package janggi.piece;

import janggi.piece.direction.FourDirection;
import janggi.piece.direction.GungDirection;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;
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
        // 목적지가 직선 상에 있는지 확인
        if (!isValidMove(destination)) {
            return false;
        }
        // 목적지에 아군이 있는지 확인
        if (!allies.isNotBlockedBy(destination)) {
            return false;
        }
        // 현재 위치와 목적지 사이의 경로 계산
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

        boolean isTwoStepStraight = (dx == 1 && dy == 0 && (enemy.isNotBlockedBy(start) || allies.isNotBlockedBy(start)) &&
                enemy.isNotBlockedBy(end))
                || (dx == 0 && dy == 1 && (enemy.isNotBlockedBy(start) || allies.isNotBlockedBy(start)) &&
                enemy.isNotBlockedBy(end));

        boolean isTwoStepDiagonal = (dx == 1 && dy == 1 && (enemy.isNotBlockedBy(start) || allies.isNotBlockedBy(start)) &&
                enemy.isNotBlockedBy(end));

        return  isTwoStepStraight || isTwoStepDiagonal;
    }

    private boolean isValidMove(JanggiPosition destination) {
        return janggiPosition.x() == destination.x() || janggiPosition.y() == destination.y();
    }

    private boolean isPoInPath(List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> piece.pieceType == pieceType);
    }
}
