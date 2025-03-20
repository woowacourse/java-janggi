package janggi.piece;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class Sa extends Piece {

    private Sa(final Position position) {
        super(PieceType.SA, position);
    }

    public static Sa from(final Position position) {
        return new Sa(position);
    }

    public static List<Sa> generateInitialSas(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.SA.getHeight());
        return PieceType.SA.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Sa(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Sa move(final Position destination, final List<Piece> enemy, final List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Sa(destination);
    }

    @Override
    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        if (!isRuleOfMove(destination)) {
            return false;
        }
        return isNotHurdle(destination, allies);
    }

    private boolean isRuleOfMove(Position destination) {
        return destination.equals(new Position(getPosition().getX() - 1, getPosition().getY()))
                || destination.equals(new Position(getPosition().getX() + 1, getPosition().getY()))
                || destination.equals(new Position(getPosition().getX(), getPosition().getY() - 1))
                || destination.equals(new Position(getPosition().getX(), getPosition().getY() + 1));
    }

    private static boolean isNotHurdle(Position destination, List<Piece> allies) {
        return allies.stream()
                .noneMatch(piece -> piece.getPosition().equals(destination));
    }
}
