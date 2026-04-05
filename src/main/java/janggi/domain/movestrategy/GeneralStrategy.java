package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.rule.MoveRule;
import janggi.domain.palace.Palace;
import janggi.domain.piece.Piece;

import java.util.List;

public class GeneralStrategy implements MoveStrategy {

    private final Palace palace;
    private final List<MoveRule> moveRules;

    public GeneralStrategy(Palace palace, List<MoveRule> moveRules) {
        this.palace = palace;
        this.moveRules = moveRules;
    }

    @Override
    public boolean canMove(Position from, Position to) {
        if (!palace.isInRange(to)) {
            return false;
        }
        return moveRules.stream()
                .anyMatch(rule -> rule.canMove(from, to));
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return moveRules.stream()
                .filter(rule -> rule.canMove(from, to))
                .map(rule -> rule.findPath(from, to))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동 경로를 찾을 수 없습니다."));
    }

    @Override
    public boolean checkPathRule(List<Piece> pathPieces) {
        return pathPieces.isEmpty();
    }
}
