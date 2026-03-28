package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.route.Paths;
import janggi.dto.PieceDTO;
import java.util.List;
import java.util.Map;

public class Piece {

    private final Side side;
    private final PieceType pieceType;
    private final String pieceNumber;

    public Piece(Side side, PieceType pieceType, String pieceNumber) {
        this.pieceType = pieceType;
        this.side = side;
        this.pieceNumber = pieceNumber;
    }

    public Paths calculatePaths(Position current) {
        return pieceType.calculatePaths(current);
    }

    public boolean isSameSide(Piece other) {
        if (other == null) {
            return false;
        }
        return this.side == other.side;
    }

    public boolean isCannon() {
        return this.pieceType == PieceType.CANNON;
    }

    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState) {
        return pieceType.determineDestinations(routes, boardState, this);
    }

    public PieceDTO mapToVO() {
        return new PieceDTO(side, pieceType, pieceNumber);
    }

    public Side getSide() {
        return side;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public String getPieceNumber() {
        return pieceNumber;
    }
}
