package model.piece;

import java.util.List;
import model.Team;

public class Cannon extends LinearMovePiece {

    private static final int CANNON_HURDLE_COUNT = 1;

    public Cannon(Team team) {
        super(team, PieceType.CANNON);
    }

    @Override
    public void validatePathCondition(List<Piece> pieces) {
        if (pieces.size() != CANNON_HURDLE_COUNT) {
            throw new IllegalArgumentException("포는 정확히 하나의 기물을 뛰어넘어야 합니다.");
        }

        boolean hasCannonAsHurdle = pieces.stream().anyMatch(piece -> piece.isSameType(this));
        if (hasCannonAsHurdle) {
            throw new IllegalArgumentException("포는 포를 다리로 쓸 수 없습니다.");
        }
    }

    @Override
    public void validateTarget(Piece otherPiece) {
        super.validateTarget(otherPiece);
        if (otherPiece.isSameType(this)) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }
}
