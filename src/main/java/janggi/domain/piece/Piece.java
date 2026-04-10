package janggi.domain.piece;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Destinations;
import janggi.domain.board.Position;
import janggi.domain.game.Side;

public record Piece(Side side, PieceType type, String pieceNumber) {

    public Piece {
        validate(side, type, pieceNumber);
    }

    private void validate(Side side, PieceType type, String pieceNumber) {
        if (side == null || type == null || pieceNumber == null) {
            throw new IllegalArgumentException("[ERROR] 기물은 유효한 [진영/타입/번호] 로만 생성 가능합니다.");
        }
        if (pieceNumber.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 기물은 유효한 번호로만 생성 가능합니다.");
        }
    }

    public Destinations determineDestinations(Position current, BoardInfo boardInfo) {
        return type.determineDestinations(current, side, boardInfo);
    }

    public boolean isAlly(Piece other) {
        if (other == null) {
            return false;
        }
        return other.side == this.side;
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

    public int getScore() {
        return type.getScore();
    }
}
