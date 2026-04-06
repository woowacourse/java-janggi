package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.rule.MoveRule;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.List;

public class CannonStrategy extends AbstractMoveStrategy {

    private final List<MoveRule> moveRules;

    public CannonStrategy(List<MoveRule> moveRules) {
        this.moveRules = moveRules;
    }

    @Override
    public boolean canMove(Position from, Position to) {
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
        return pathPieces.size() == 1 &&
                !pathPieces.getFirst().isSameType(PieceType.CANNON);
    }

    @Override
    public boolean canCapture(Piece from, Piece to) {
        if (to == null) {
            return true;
        }
        if (from.isSameTeam(to)) {
            return false;
        }
        return !to.isSameType(PieceType.CANNON);
    }
}
