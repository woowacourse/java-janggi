package janggi.piece;

import janggi.direction.OneStepDirection;
import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class Sa extends Piece {

    public Sa(final Position position) {
        super(PieceType.SA, position);
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
