package janggi;

import java.util.List;

public class Sang implements Piece {

    private static final int score = 3;
    private static final int height = 0;

    private final PieceType pieceType;
    private final Position position;

    private Sang(final Position position) {
        this.pieceType = PieceType.SANG;
        this.position = position;
    }

    public static List<Sang> generateInitialPos(final CampType campType, final List<Integer> xPositions) {
        int yPosition = Math.abs(campType.getStartYPosition() - height);
        return xPositions.stream()
                .map(xPosition -> new Sang(new Position(xPosition, yPosition)))
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
