package janggi.piece;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class Jol implements Piece {

    private static final int score = 2;
    private static final int height = 3;
    private static final List<Integer> xPositions = List.of(0, 2, 4, 6, 8);

    private final PieceType pieceType;
    private final Position position;
    private final CampType campType;

    private Jol(final Position position, final CampType campType) {
        this.pieceType = PieceType.JOL;
        this.position = position;
        this.campType = campType;
    }

    public static Jol from(final Position position, final CampType campType) {
        return new Jol(position, campType);
    }

    public static List<Jol> generateInitialJols(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - height);
        return xPositions.stream()
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
        if (!isRuleOfMove(destination)) {
            return false;
        }
        return isNotHurdle(destination, allies);
    }

    private boolean isRuleOfMove(Position destination) {
        if (campType == CampType.CHO) {
            return destination.equals(new Position(position.getX() - 1, position.getY()))
                    || destination.equals(new Position(position.getX() + 1, position.getY()))
                    || destination.equals(new Position(position.getX(), position.getY() - 1));
        }
        return destination.equals(new Position(position.getX() - 1, position.getY()))
                || destination.equals(new Position(position.getX() + 1, position.getY()))
                || destination.equals(new Position(position.getX(), position.getY() + 1));
    }

    private static boolean isNotHurdle(Position destination, List<Piece> allies) {
        return allies.stream()
                .noneMatch(piece -> piece.getPosition().equals(destination));
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
