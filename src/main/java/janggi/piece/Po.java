package janggi.piece;

import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;
import java.util.stream.IntStream;

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
        if (!isStraightLine(destination)) {
            return false;
        }

        // 목적지에 아군이 있는지 확인
        if (allies.isPositionOccupiedByEnemy(destination)) {
            return false;
        }
//        if (isAlliesInDestination(destination, allies)) {
//            return false;
//        }

        // 현재 위치와 목적지 사이의 경로 계산
        List<JanggiPosition> pathPositions = calculatePathPositions(destination);

        if (!allies.isPieceInPathEmpty(pathPositions, enemy.getPieces())) {
            return false;
        }

//        // 경로 상의 아군과 적군 검색
//        List<Piece> alliesInPath = searchPiecesInPath(allies, pathPositions);
//        List<Piece> enemyInPath = searchPiecesInPath(enemy, pathPositions);
//
//        // 경로 상에 아군이나 적군이 없으면 이동 불가
//        if (alliesInPath.isEmpty() && enemyInPath.isEmpty()) {
//            return false;
//        }

        if (allies.isPoInPath(pathPositions, enemy.getPieces())) {
            return false;
        }
//        // 경로 상에 포가 있는지 확인
//        if (containsPo(alliesInPath) || containsPo(enemyInPath)) {
//            return false;
//        }

//        // 경로 상의 말이 딱 하나인지 확인
//        return alliesInPath.size() + enemyInPath.size() == 1;
        return allies.isOnlyOnePieceInPath(pathPositions, enemy.getPieces(), allies.getPieces());
    }

    private boolean isStraightLine(JanggiPosition destination) {
        return getPosition().getX() == destination.getX() || getPosition().getY() == destination.getY();
    }

    private boolean isAlliesInDestination(JanggiPosition destination, List<Piece> allies) {
        return allies.stream()
                .anyMatch(piece -> piece.getPosition().equals(destination));
    }

    private List<JanggiPosition> calculatePathPositions(JanggiPosition destination) {
        int startX = getPosition().getX();
        int startY = getPosition().getY();
        int endX = destination.getX();
        int endY = destination.getY();

        if (startX == endX) {
            return generatePositionsAlongY(startX, startY, endY);
        }
        return generatePositionsAlongX(startY, startX, endX);
    }

    private List<JanggiPosition> generatePositionsAlongY(int x, int startY, int endY) {
        int minY = Math.min(startY, endY);
        int maxY = Math.max(startY, endY);
        return IntStream.rangeClosed(minY + 1, maxY - 1)
                .filter(y -> y > 0)
                .mapToObj(y -> new JanggiPosition(x, y))
                .toList();
    }

    private List<JanggiPosition> generatePositionsAlongX(int y, int startX, int endX) {
        int minX = Math.min(startX, endX);
        int maxX = Math.max(startX, endX);
        return IntStream.rangeClosed(minX + 1, maxX - 1)
                .filter(x -> x > 0)
                .mapToObj(x -> new JanggiPosition(x, y))
                .toList();
    }

    private List<Piece> searchPiecesInPath(List<Piece> pieces, List<JanggiPosition> pathPositions) {
        return pieces.stream()
                .filter(piece -> pathPositions.contains(piece.getPosition()))
                .toList();
    }

    private boolean containsPo(List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> piece.checkPieceType(PieceType.PO));
    }
}