package player;

import pieceProperty.Position;
import pieceProperty.Positions;

public class Player {
    private final Pieces pieces;

    public Player(final Pieces pieces) {
        this.pieces = pieces;
    }

    public void movePiece(final Position presentPosition, final Position destination) {
        pieces.movePiece(presentPosition, destination);
    }

    public void validateAllyPieceAtStart(final Position presentPosition) {
        pieces.validateAllyPieceAtStart(presentPosition);
    }

    public void validateAllyPieceAtDestination(final Position destination) {
        pieces.validateAllyPieceAtDestination(destination);
    }

    public void canPieceMoveTo(final Position presentPosition, final Position destination) {
        pieces.canPieceMoveTo(presentPosition, destination);
    }

    public int countObstacle(final Positions route) {
        return pieces.countObstacle(route);
    }

    public Positions makeRoute(final Position presentPosition, final Position destination) {
        return pieces.makeRoute(presentPosition, destination);
    }

    public void removePiece(final Position destination) {
        pieces.removePiece(destination);
    }

    public boolean isJanggunDie() {
        return pieces.hasJanggun();
    }

    public Boolean isPoAt(final Position presentPosition) {
        return pieces.isPoAt(presentPosition);
    }

    public Boolean isExistPoInRoute(final Positions route) {
        return pieces.isExistPoInRoute(route);
    }

    public Pieces getPieces() {
        return pieces;
    }

}
