package janggi;

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
    public void move(Position destination, List<Piece> enemy, List<Piece> allies) {

    }

    @Override
    public void ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {

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
