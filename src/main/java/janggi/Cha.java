package janggi;

import java.util.List;

public class Cha implements Piece {

    private static final int score = 13;
    private static final int height = 0;
    private static final List<Integer> xPositions = List.of(0, 8);

    private final PieceType pieceType;
    private final Position position;

    private Cha(final Position position) {
        this.pieceType = PieceType.CHA;
        this.position = position;
    }

    public static List<Cha> generateInitialChas(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - height);
        return xPositions.stream()
                .map(xPosition -> new Cha(new Position(xPosition, yPosition)))
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
