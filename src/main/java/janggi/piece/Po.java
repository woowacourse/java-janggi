package janggi.piece;

import janggi.piece.direction.FourDirection;
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
        return new Po(destination);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, Pieces enemy, Pieces allies) {
        // 목적지가 직선 상에 있는지 확인
        if (!isValidMove(destination)) {
            return false;
        }
        // 목적지에 아군이 있는지 확인
        if (!allies.isNotBlockedBy(destination)) {
            return false;
        }
        // 현재 위치와 목적지 사이의 경로 계산
        List<JanggiPosition> pathPositions = FourDirection.from(destination, getPosition());

        List<Piece> alliesInPath = allies.searchPiecesInPath(pathPositions);
        List<Piece> enemyInPath = enemy.searchPiecesInPath(pathPositions);

        if (isPoInPath(alliesInPath) || isPoInPath(enemyInPath)) {
            return false;
        }

        return alliesInPath.size() + enemyInPath.size() == 1;
    }

    private boolean isValidMove(JanggiPosition destination) {
        return getPosition().x() == destination.x() || getPosition().y() == destination.y();
    }

    private boolean isPoInPath(List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> piece.getPieceType() == getPieceType());
    }
}
