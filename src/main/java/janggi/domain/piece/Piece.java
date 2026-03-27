package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.route.Paths;
import java.util.List;
import java.util.Map;

public class Piece {

    private final Side side;
    private final PieceType type;
    private final String pieceNumber;

    public Piece(Side side, PieceType type, String pieceNumber) {
        validate(side, type, pieceNumber);
        this.side = side;
        this.type = type;
        this.pieceNumber = pieceNumber;
    }

    private void validate(Side side, PieceType type, String pieceNumber) {
        if (side == null || type == null || pieceNumber == null) {
            throw new IllegalArgumentException("[ERROR] 기물은 유효한 [진영/타입/번호] 로만 생성 가능합니다.");
        }

        if (pieceNumber.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 기물은 유효한 번호로만 생성 가능합니다.");
        }
    }

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
        return other.isSameSide(side);
    }

    private boolean isSameSide(Side otherSide) {
        return side == otherSide;
    }

    public boolean isCannon() {
        return type == PieceType.CANNON;
    }

    public boolean isPalace() {
        return type == PieceType.PALACE;
    }

    public boolean isBelongTo(Side side) {
        return this.side == side;
    }

    public <T> T map(PieceMapper<T> mapper) {
        return mapper.apply(this.side, this.type, this.pieceNumber);
    }
}
