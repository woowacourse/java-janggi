package janggi.piece;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class Po implements Piece {

    private static final int score = 7;
    private static final int height = 2;
    private static final List<Integer> xPositions = List.of(1, 7);

    private final PieceType pieceType;
    private final Position position;

    private Po(final Position position) {
        this.pieceType = PieceType.PO;
        this.position = position;
    }

    public static List<Po> generateInitialPos(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - height);
        return xPositions.stream()
                .map(xPosition -> new Po(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    public void move(Position destination, List<Piece> enemy, List<Piece> allies) {

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
