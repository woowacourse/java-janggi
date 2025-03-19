package janggi;

import java.util.List;

public class Ma implements Piece {

    private static final int score = 5;
    private static final int height = 0;

    private final PieceType pieceType;
    private final Position position;

    private Ma(final Position position) {
        this.pieceType = PieceType.MA;
        this.position = position;
    }

    public static List<Ma> generateInitialPos(final CampType campType, final List<Integer> xPositions) {
        int yPosition = Math.abs(campType.getStartYPosition() - height);
        return xPositions.stream()
                .map(xPosition -> new Ma(new Position(xPosition, yPosition)))
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
