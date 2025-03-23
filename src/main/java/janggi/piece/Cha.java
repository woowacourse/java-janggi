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
    public Cha move(final JanggiPosition destination, final List<Piece> enemyPieces, final List<Piece> allyPieces) {
        if (!ableToMove(destination, enemyPieces, allyPieces)) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Cha(destination);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, List<Piece> enemyPieces, List<Piece> allyPieces) {
        if (!isValidMove(destination)) {
            return false;
        }
        return isPathBlockedByAlly(destination, allyPieces) && isPathClearOrBlockedByEnemy(destination, enemyPieces);
    }

    private boolean isValidMove(JanggiPosition destination) {
        return getPosition().getX() == destination.getX() || getPosition().getY() == destination.getY();
    }

    private boolean isPathBlockedByAlly(JanggiPosition destination, List<Piece> allyPieces) {
        List<JanggiPosition> pathPositions = calculatePathPositions(destination);
        return pathPositions.stream()
                .noneMatch(position -> isPositionOccupiedByAlly(position, allyPieces));
    }

    private boolean isPathClearOrBlockedByEnemy(JanggiPosition destination, List<Piece> enemyPieces) {
        List<JanggiPosition> pathPositions = calculatePathPositions(destination);
        for (JanggiPosition position : pathPositions) {
            if (position.equals(destination)) {
                // 목적지에는 상대방이 있으면 공격 가능 (2.1)
                continue;
            }
            if (isPositionOccupiedByEnemy(position, enemyPieces)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPositionOccupiedByAlly(JanggiPosition position, List<Piece> allyPieces) {
        return allyPieces.stream().anyMatch(piece -> piece.getPosition().equals(position));
    }

    private boolean isPositionOccupiedByEnemy(JanggiPosition position, List<Piece> enemyPieces) {
        return enemyPieces.stream().anyMatch(piece -> piece.getPosition().equals(position));
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