package domain.strategy;

import domain.PieceProvider;
import domain.Team;
import domain.position.Position;
import java.util.List;

public class NoStrategy implements Strategy {

    @Override
    public List<Position> getMoveCandidates(Position from, Team team, PieceProvider board) {
        throw new IllegalArgumentException("빈 칸은 이동할 수 없습니다.");
    }
}
