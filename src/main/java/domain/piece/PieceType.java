package domain.piece;

import domain.Moves;
import domain.piece.movement.MaMoveStrategy;
import domain.piece.movement.MoveStrategy;
import domain.piece.movement.PalaceMoveStrategy;
import domain.piece.movement.PawnMoveStrategy;
import domain.piece.movement.SangMoveStrategy;
import domain.piece.movement.StraightMoveStrategy;
import domain.piece.rule.DefaultRule;
import domain.piece.rule.PalaceRule;
import domain.piece.rule.Rule;
import java.util.Arrays;
import java.util.List;

public enum PieceType {

    CHA(13, new StraightMoveStrategy(), new DefaultRule()),
    GUNG(0, new PalaceMoveStrategy(), new PalaceRule()),
    MA(5, new MaMoveStrategy(), new DefaultRule()),
    PAWN(2, new PawnMoveStrategy(), new DefaultRule()),
    PO(7, new StraightMoveStrategy(), new DefaultRule()),
    SA(3, new PalaceMoveStrategy(), new PalaceRule()),
    SANG(3, new SangMoveStrategy(), new DefaultRule());

    PieceType(int score, MoveStrategy moveStrategy, Rule rule) {
        this.score = score;
        this.moveStrategy = moveStrategy;
        this.rule = rule;
    }

    private final int score;
    private final MoveStrategy moveStrategy;
    private final Rule rule;

    public static PieceType find(String value) {
        return Arrays.stream(values())
                .filter(type -> type.toString().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 타입이 없습니다."));
    }

    public List<Moves> findPossibleMoves(Position src, Position dest, Team team) {
        return moveStrategy.findPossibleMoves(src, dest, team);
    }

    public void applyRule(Moves moves, Position src, Position dest) {
        rule.validate(moves, src, dest);
    }

    public int getScore() {
        return score;
    }
}
