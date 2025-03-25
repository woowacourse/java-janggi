package janggi.piece;

import janggi.piece.direction.FourDirection;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;

public class Byeong extends Piece {

    private Byeong(final JanggiPosition janggiPosition) {
        super(PieceType.BYEONG, janggiPosition);
    }

    public static Byeong from(final JanggiPosition janggiPosition) {
        return new Byeong(janggiPosition);
    }

    public static List<Byeong> generateInitialByeongs(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.BYEONG.getHeight());
        return PieceType.BYEONG.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Byeong(new JanggiPosition(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Byeong move(final JanggiPosition destination, final Pieces enemyPieces, final Pieces allyPieces) {
        if (!ableToMove(destination, enemyPieces, allyPieces)) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Byeong(destination);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, Pieces enemyPieces, Pieces allyPieces) {
        List<JanggiPosition> pathPositions = FourDirection.from(destination, getPosition());
        return isValidMove(pathPositions) && allyPieces.isNotBlockedBy(destination);
    }

    private boolean isValidMove(List<JanggiPosition> pathPositions) {
        int currentX = getPosition().x();
        int currentY = getPosition().y();
        int destX = pathPositions.getLast().x();
        int destY = pathPositions.getLast().y();

        return (destX == currentX && destY == currentY + 1) || (destY == currentY && Math.abs(destX - currentX) == 1);
    }
}
