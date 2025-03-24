package player;

import pieceProperty.Position;
import pieceProperty.Positions;

public class Player {
    private final Pieces pieces;
    private final Nation nation;

    public Player(final Pieces pieces, final Nation nation) {
        this.pieces = pieces;
        this.nation = nation;
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

    public boolean isSameNation(final Nation nation) {
        return this.nation.equals(nation);
    }

    public boolean isKingDie() {
        return pieces.isKingDie();
    }

    public Pieces getPieces() {
        return pieces;
    }

    public Boolean isPoAt(final Position presentPosition) {
        return pieces.isPoAt(presentPosition);
    }

    public Boolean isExistPoInRoute(final Positions route) {
        return pieces.isExistPoInRoute(route);
    }

}
