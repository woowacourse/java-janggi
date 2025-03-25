package janggi.piece;

import janggi.piece.direction.FourDirection;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;

public class Sa extends Piece {

    private Sa(final JanggiPosition janggiPosition) {
        super(PieceType.SA, janggiPosition);
    }

    public static Sa from(final JanggiPosition janggiPosition) {
        return new Sa(janggiPosition);
    }

    public static List<Sa> generateInitialSas(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.SA.getHeight());
        return PieceType.SA.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Sa(new JanggiPosition(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Sa move(final JanggiPosition destination, final Pieces enemyPieces, final Pieces allyPieces) {
        if (!ableToMove(destination, enemyPieces, allyPieces)) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Sa(destination);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, Pieces enemyPieces, Pieces allyPieces) {
        List<JanggiPosition> pathPositions = FourDirection.from(destination, getPosition());
        return isValidMove(pathPositions) && allyPieces.isNotBlockedBy(destination);
    }

    private boolean isValidMove(List<JanggiPosition> pathPositions) {
        return pathPositions.size() == 1;
    }
}
