package janggi.piece;

import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;
import java.util.stream.IntStream;

public class Cha extends Piece {

    private Cha(final JanggiPosition janggiPosition) {
        super(PieceType.CHA, janggiPosition);
    }

    public static Cha from(final JanggiPosition janggiPosition) {
        return new Cha(janggiPosition);
    }

    public static List<Cha> generateInitialChas(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.CHA.getHeight());
        return PieceType.CHA.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Cha(new JanggiPosition(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Cha move(final JanggiPosition destination, final Pieces enemyPieces, final Pieces allyPieces) {
        if (!ableToMove(destination, enemyPieces, allyPieces)) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Cha(destination);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, Pieces enemyPieces, Pieces allyPieces) {
        if (!isValidMove(destination)) {
            return false;
        }

        List<JanggiPosition> pathPositions = calculatePathPositions(destination);

        // 목적지에 적이 있는지 확인
        boolean isDestinationOccupiedByEnemy = enemyPieces.isPositionOccupiedByEnemy(destination);

        if (isDestinationOccupiedByEnemy) {
            // 목적지에 적이 있는 경우, 경로 상에 아군이 없어야 함
            return allyPieces.isPathBlockedByAlly(pathPositions);
        }
        // 목적지에 적이 없는 경우, 경로 상에 아군이나 적이 없어야 함
        return allyPieces.isPathBlockedByAlly(pathPositions)
                && enemyPieces.isPathBlockedByEnemy(pathPositions);
    }

    private boolean isValidMove(JanggiPosition destination) {
        return getPosition().getX() == destination.getX() || getPosition().getY() == destination.getY();
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
        return IntStream.rangeClosed(minY, maxY)
                .mapToObj(y -> new JanggiPosition(x, y))
                .toList();
    }

    private List<JanggiPosition> generatePositionsAlongX(int y, int startX, int endX) {
        int minX = Math.min(startX, endX);
        int maxX = Math.max(startX, endX);
        return IntStream.rangeClosed(minX, maxX)
                .mapToObj(x -> new JanggiPosition(x, y))
                .toList();
    }
}