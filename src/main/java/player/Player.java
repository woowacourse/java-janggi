package player;

import pieceProperty.Position;
import pieceProperty.Positions;

public class Player {
    private final JanggiPan pieces;

    public Player(JanggiPan pieces) {
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

    public void removePiece(Position destination) {
        pieces.removePiece(destination);
    }

    public int countObstacle(final Positions route) {
        return pieces.countObstacle(route);
    }

    public Positions makeRoute(final Position presentPosition, final Position destination) {
        return pieces.makeRoute(presentPosition, destination);
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

    public JanggiPan getPieces() {
        return pieces;
    }

}
