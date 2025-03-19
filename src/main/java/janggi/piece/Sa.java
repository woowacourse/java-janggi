package janggi.piece;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class Sa implements Piece {

    private static final int score = 3;
    private static final int height = 0;
    private static final List<Integer> xPositions = List.of(3, 5);

    private final PieceType pieceType;
    private final Position position;

    private Sa(final Position position) {
        this.pieceType = PieceType.SA;
        this.position = position;
    }

    public static Sa from(final Position position) {
        return new Sa(position);
    }

    public static List<Sa> generateInitialSas(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - height);
        return xPositions.stream()
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
        return destination.equals(new Position(position.getX() - 1, position.getY()))
                || destination.equals(new Position(position.getX() + 1, position.getY()))
                || destination.equals(new Position(position.getX(), position.getY() - 1))
                || destination.equals(new Position(position.getX(), position.getY() + 1));
    }

    private static boolean isNotHurdle(Position destination, List<Piece> allies) {
        return allies.stream()
                .noneMatch(piece -> piece.getPosition().equals(destination));
    }

    @Override
    public boolean checkPieceType(PieceType pieceType) {
        return this.pieceType == pieceType;
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
