package domain.piece;

import domain.Moves;
import domain.piece.movement.MaMoveStrategy;
import domain.piece.movement.MoveStrategy;
import domain.piece.movement.PalaceMoveStrategy;
import domain.piece.movement.PawnMoveStrategy;
import domain.piece.movement.SangMoveStrategy;
import domain.piece.movement.StraightMoveStrategy;
import java.util.Arrays;
import java.util.List;

public enum PieceType {

    CHA(13, new StraightMoveStrategy()),
    GUNG(0, new PalaceMoveStrategy()),
    MA(5, new MaMoveStrategy()),
    PAWN(2, new PawnMoveStrategy()),
    PO(7, new StraightMoveStrategy()),
    SA(3, new PalaceMoveStrategy()),
    SANG(3, new SangMoveStrategy());

    PieceType(int score, MoveStrategy moveStrategy) {
        this.score = score;
        this.moveStrategy = moveStrategy;
    }

    private final int score;
    private final MoveStrategy moveStrategy;

    public static PieceType find(String value) {
        return Arrays.stream(values())
                .filter(type -> type.toString().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 타입이 없습니다."));
    }

    public List<Moves> findPossibleMoves(Position src, Position dest, Team team) {
        return moveStrategy.findPossibleMoves(src, dest, team);
    }

    public int getScore() {
        return score;
    }
}
