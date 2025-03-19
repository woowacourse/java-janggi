package domain.piece.strategy;

import domain.BoardLocation;
import domain.piece.MoveStrategy;

public class KingMoveStrategy implements MoveStrategy {

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        return false; //TODO 2단계 궁성 단계에서 처리하도록 하기
    }
}
