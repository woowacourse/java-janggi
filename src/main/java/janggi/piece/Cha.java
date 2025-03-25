package janggi.piece;

import janggi.piece.direction.FourDirection;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;

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
        List<JanggiPosition> pathPositions = FourDirection.from(destination, getPosition());
        if (!enemyPieces.isNotBlockedBy(destination)) {
            // 목적지에 적이 있는 경우, 경로 상에 아군이 없어야 함
            return allyPieces.isPathBlockedBy(pathPositions);
        }
        return allyPieces.isPathBlockedBy(pathPositions)
                && enemyPieces.isPathBlockedBy(pathPositions);
    }

    private boolean isValidMove(JanggiPosition destination) {
        return getPosition().x() == destination.x() || getPosition().y() == destination.y();
    }
}
