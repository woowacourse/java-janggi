package domain.piece;

import domain.Moves;
import domain.piece.movement.MaMoveStrategy;
import domain.piece.movement.MoveStrategy;
import domain.piece.movement.PalaceMoveStrategy;
import domain.piece.movement.PawnMoveStrategy;
import domain.piece.movement.SangMoveStrategy;
import domain.piece.movement.StraightMoveStrategy;
import domain.piece.rule.JumpOverOnePieceRule;
import domain.piece.rule.NoJumpRule;
import domain.piece.rule.PalaceRule;
import domain.piece.rule.Rule;
import domain.piece.rule.SameTeamAttackRule;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum PieceType {

    CHA(13, new StraightMoveStrategy(), List.of(new NoJumpRule(), new SameTeamAttackRule())),
    GUNG(0, new PalaceMoveStrategy(), List.of(new PalaceRule(), new NoJumpRule(), new SameTeamAttackRule())),
    MA(5, new MaMoveStrategy(), List.of(new NoJumpRule(), new SameTeamAttackRule())),
    PAWN(2, new PawnMoveStrategy(), List.of(new NoJumpRule(), new SameTeamAttackRule())),
    PO(7, new StraightMoveStrategy(), List.of(new JumpOverOnePieceRule(), new SameTeamAttackRule())),
    SA(3, new PalaceMoveStrategy(), List.of(new PalaceRule(), new NoJumpRule(), new SameTeamAttackRule())),
    SANG(3, new SangMoveStrategy(), List.of(new NoJumpRule(), new SameTeamAttackRule()));

    PieceType(int score, MoveStrategy moveStrategy, List<Rule> rules) {
        this.score = score;
        this.moveStrategy = moveStrategy;
        this.rules = rules;
    }

    private final int score;
    private final MoveStrategy moveStrategy;
    private final List<Rule> rules;

    public static PieceType find(String value) {
        return Arrays.stream(values())
                .filter(type -> type.toString().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 타입이 없습니다."));
    }

    public List<Moves> findPossibleMoves(Position src, Position dest, Team team) {
        return moveStrategy.findPossibleMoves(src, dest, team);
    }

    public void applyRule(List<Position> path, List<Piece> piecesInPath, Piece selectedPiece,
                          Optional<Piece> targetPiece) {
        rules.forEach(rule -> rule.validate(path, piecesInPath, selectedPiece, targetPiece));
    }

    public int getScore() {
        return score;
    }
}
