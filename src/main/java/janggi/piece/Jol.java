package janggi.piece;

import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;

public class Jol extends Piece {

    private final CampType campType;

    private Jol(final JanggiPosition janggiPosition, final CampType campType) {
        super(PieceType.JOL, janggiPosition);
        this.campType = campType;
    }

    public static Jol from(final JanggiPosition janggiPosition, final CampType campType) {
        return new Jol(janggiPosition, campType);
    }

    public static List<Jol> generateInitialJols(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.JOL.getHeight());
        return PieceType.JOL.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Jol(new JanggiPosition(xPosition, yPosition), campType))
                .toList();
    }

    @Override
    public Jol move(final JanggiPosition destination, final List<Piece> enemyPieces, final List<Piece> allyPieces) {
        if (!ableToMove(destination, enemyPieces, allyPieces)) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Jol(destination, campType);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, List<Piece> enemyPieces, List<Piece> allyPieces) {
        return isValidMove(destination) && isNotBlockedByAlly(destination, allyPieces);
    }

    private boolean isValidMove(JanggiPosition destination) {
        int currentX = getPosition().getX();
        int currentY = getPosition().getY();
        int destX = destination.getX();
        int destY = destination.getY();

        if (campType == CampType.CHO) {
            return (destX == currentX && destY == currentY - 1)
                    || (destY == currentY && Math.abs(destX - currentX) == 1);
        }
        return (destX == currentX && destY == currentY + 1)
                || (destY == currentY && Math.abs(destX - currentX) == 1);
    }

    private boolean isNotBlockedByAlly(JanggiPosition destination, List<Piece> allyPieces) {
        return allyPieces.stream()
                .noneMatch(piece -> piece.getPosition().equals(destination));
    }
}