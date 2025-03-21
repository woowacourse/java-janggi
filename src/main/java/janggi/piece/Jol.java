package janggi.piece;

import janggi.direction.OneStepDirection;
import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class Jol extends Piece {

    private final CampType campType;

    private Jol(final Position position, final CampType campType) {
        super(PieceType.JOL, position);
        this.campType = campType;
    }

    public static Jol from(final Position position, final CampType campType) {
        return new Jol(position, campType);
    }

    public static List<Jol> generateInitialJols(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.JOL.getHeight());
        return PieceType.JOL.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Jol(new Position(xPosition, yPosition), campType))
                .toList();
    }

    @Override
    public Jol move(final Position destination, final List<Piece> enemy, final List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Jol(destination, campType);
    }

    @Override
    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        boolean followRuleOfMove = checkRuleOfMove(destination);
        boolean existHurdleInDestination = existHurdleInPosition(destination, allies);
        return followRuleOfMove && !existHurdleInDestination;
    }

    private boolean checkRuleOfMove(Position destination) {
        OneStepDirection direction = OneStepDirection.parse(getPosition(), destination);
        if (direction == OneStepDirection.NONE) {
            return false;
        }
        if (campType == CampType.CHO) {
            System.out.println(direction);
            System.out.println(direction != OneStepDirection.DOWN);
            return direction != OneStepDirection.DOWN;
        }
        return direction != OneStepDirection.UP;
    }

    private boolean existHurdleInPosition(Position destination, List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> piece.getPosition().equals(destination));
    }
}
