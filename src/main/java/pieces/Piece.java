package pieces;

import java.util.List;
import movepolicy.rule.MoveTrace;
import participant.Score;
import position.Position;

public record Piece(Side side, PieceType type) {

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public boolean isSameSide(Piece piece) {
        return isSameSide(piece.side);
    }

    public boolean isPo() {
        return type.isPo();
    }

    public Score getScore() {
        return type.getScore();
    }

    public void validate(Position departure, Position destination, MoveTrace moveTrace) {
        if (!type.getMovement().canReach(departure, destination, side)) {
            throw new IllegalArgumentException("행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
        type.getMoveRule().validate(moveTrace);
    }

    public List<Position> findPathPositions(Position departure, Position destination) {
        return type.getMovement().findPathPositions(departure, destination, side);
    }
}
