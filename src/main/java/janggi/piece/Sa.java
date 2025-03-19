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

    public static List<Sa> generateInitialSas(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - height);
        return xPositions.stream()
                .map(xPosition -> new Sa(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Sa move(Position destination, List<Piece> enemy, List<Piece> allies) {
        return new Sa(destination);
    }

    @Override
    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        return true;
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
