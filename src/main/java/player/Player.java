package player;

import pieceProperty.Position;
import pieceProperty.Positions;

public class Player {
    private final Pieces pieces;
    private final Nation nation;

    public Player(Pieces pieces, Nation nation) {
        this.pieces = pieces;
        this.nation = nation;
    }

    public void movePiece(Position presentPosition, Position destination) {
        pieces.movePiece(presentPosition, destination);
    }

    public void validateAllyPieceAtStart(Position presentPosition) {
        pieces.validateAllyPieceAtStart(presentPosition);
    }

    public void validateAllyPieceAtDestination(Position destination) {
        pieces.validateAllyPieceAtDestination(destination);
    }

    public void canPieceMoveTo(Position presentPosition, Position destination) {
        pieces.canPieceMoveTo(presentPosition, destination);
    }

    public int countObstacle(Positions route) {
        return pieces.countObstacle(route);
    }

    public Positions makeRoute(Position presentPosition, Position destination) {
        return pieces.makeRoute(presentPosition, destination);
    }

    public void removePiece(Position destination) {
    }

    public boolean isSameNation(Nation nation) {
        return this.nation.equals(nation);
    }

    public boolean isKingDie() {
        return pieces.isKingDie();
    }

    public Pieces getPieces() {
        return pieces;
    }
}
