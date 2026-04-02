package domain.strategy;

import domain.PieceProvider;
import domain.position.Position;
import java.util.List;

public class NoStrategy implements Strategy {

    @Override
    public List<Position> getMoveCandidates(Position from, PieceProvider board) {
        throw new IllegalArgumentException("빈 칸은 이동할 수 없습니다.");
    }
}
