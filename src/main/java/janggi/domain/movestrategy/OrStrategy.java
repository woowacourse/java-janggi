package janggi.domain.movestrategy;

import janggi.domain.board.BoardState;
import janggi.domain.position.Position;

public class OrStrategy implements MoveStrategy {
    private final MoveStrategy strategyA;
    private final MoveStrategy strategyB;

    public OrStrategy(MoveStrategy strategyA, MoveStrategy strategyB) {
        this.strategyA = strategyA;
        this.strategyB = strategyB;
    }

    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        return strategyA.canMove(from, to, boardState) || strategyB.canMove(from, to, boardState);
    }
}
