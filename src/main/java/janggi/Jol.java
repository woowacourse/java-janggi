package janggi;

import java.util.List;

public class Jol implements Piece {

    private static final int score = 2;
    private static final int height = 3;
    private static final List<Integer> xPositions = List.of(0, 2, 4, 6, 8);

    private final PieceType pieceType;
    private final Position position;

    private Jol(final Position position) {
        this.pieceType = PieceType.JOL;
        this.position = position;
    }

    public static List<Jol> generateInitialJols(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - height);
        return xPositions.stream()
                .map(xPosition -> new Jol(new Position(xPosition, yPosition)))
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
