package janggi.piece;

import janggi.direction.OneStepDirection;
import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class Gung extends Piece {

    public Gung(final Position position) {
        super(PieceType.GUNG, position);
    }

    public static List<Gung> generateInitialGung(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.GUNG.getHeight());
        return PieceType.GUNG.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Gung(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Gung move(final Position destination, final List<Piece> enemy, final List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Gung(destination);
    }

    @Override
    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        OneStepDirection direction = OneStepDirection.parse(getPosition(), destination);

        boolean followRuleOfMove = checkRuleOfMove(direction);
        boolean existAlliesInDestination = existPieceInDestination(destination, allies);
        return followRuleOfMove && !existAlliesInDestination;
    }

    private boolean checkRuleOfMove(OneStepDirection direction) {
        return direction != OneStepDirection.NONE;
    }

    private boolean existPieceInDestination(Position destination, List<Piece> allies) {
        return allies.stream()
                .anyMatch(piece -> piece.getPosition().equals(destination));
    }
}
