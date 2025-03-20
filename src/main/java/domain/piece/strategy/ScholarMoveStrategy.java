package domain.piece.strategy;

import domain.BoardLocation;
import domain.piece.MoveStrategy;
import java.util.List;

public class ScholarMoveStrategy implements MoveStrategy {

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        return false; //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        return List.of(); //TODO 2단계 궁성 단계에서 처리하도록 하기
    }
}
