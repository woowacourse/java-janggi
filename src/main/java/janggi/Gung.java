package janggi;

import java.util.List;

public class Gung implements Piece {

    private static final int score = 0;
    private static final int height = 1;
    private static final List<Integer> xPositions = List.of(4);

    private final PieceType pieceType;
    private final Position position;

    private Gung(final Position position) {
        this.pieceType = PieceType.GUNG;
        this.position = position;
    }

    public static List<Gung> generateInitialGung(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - height);
        return xPositions.stream()
                .map(xPosition -> new Gung(new Position(xPosition, yPosition)))
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
