package janggi.domain.board.maSangStrategy;

import janggi.domain.Coordinate;
import janggi.domain.Team;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import java.util.List;
import java.util.Set;

public class SangMaSangMa implements MaSangStrategy {

    @Override
    public Set<Piece> createMaAndSang(Team team) {
        List<Integer> x = TEAM_X_COORDINATE.get(team);
        int y = TEAM_Y_COORDINATE.get(team);

        return Set.of(
            new Piece(team, new Coordinate(x.get(0), y), PieceType.SANG),
            new Piece(team, new Coordinate(x.get(1), y), PieceType.MA),
            new Piece(team, new Coordinate(x.get(2), y), PieceType.SANG),
            new Piece(team, new Coordinate(x.get(3), y), PieceType.MA)
        );
    }
}
