package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.route.Paths;
import java.util.List;
import java.util.Map;

public record Piece(Side side, PieceType type, String pieceNumber) {

    public Paths calculatePaths(Position current) {
        return type.calculatePaths(current);
    }

    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState) {
        return type.determineDestinations(routes, boardState, this);
    }

    public boolean isSameSide(Piece other) {
        if (other == null) {
            return false;
        }
        return this.side == other.side();
    }

    public boolean isCannon() {
        return this.type == PieceType.CANNON;
    }
}
