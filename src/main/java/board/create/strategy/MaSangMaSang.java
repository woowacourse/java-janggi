package board.create.strategy;

import coordinate.Coordinate;
import java.util.List;
import java.util.Map;
import piece.Ma;
import piece.Piece;
import piece.Sang;
import team.Team;

public class MaSangMaSang extends TableSettingStrategy {

    @Override
    public Map<Coordinate, Piece> create(Team team) {
        List<Integer> x = team.getMaSangXCoordinates();
        int y = team.getMaSangYCoordinate();

        Map<Coordinate, Piece> pieces = super.create(team);
        pieces.put(new Coordinate(x.get(0), y), new Ma(team));
        pieces.put(new Coordinate(x.get(1), y), new Sang(team));
        pieces.put(new Coordinate(x.get(2), y), new Ma(team));
        pieces.put(new Coordinate(x.get(3), y), new Sang(team));

        return pieces;
    }
}
