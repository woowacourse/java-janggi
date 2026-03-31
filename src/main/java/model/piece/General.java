package model.piece;

import java.util.List;
import model.Team;
import model.coordinate.Position;

public class General extends Piece {

    public General(Team team) {
        super(team, PieceType.GENERAL);
    }

    @Override
    public List<Position> extractPath(Position current, Position next) {
        throw new IllegalArgumentException("1단계 궁성 영역 미구현");
    }

    @Override
    protected boolean isReachable(int rowDiff, int colDiff) {
        throw new IllegalArgumentException("1단계 궁성 영역 미구현");
    }
}
