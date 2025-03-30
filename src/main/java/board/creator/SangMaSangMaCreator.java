package board.creator;

import coordinate.Coordinate;
import java.util.List;
import java.util.Map;
import piece.Piece;
import piece.PieceType;
import team.Team;

public class SangMaSangMaCreator extends TableSettingCreator {

    @Override
    public Map<Coordinate, Piece> create(Team team) {
        List<Integer> x = team.getMaSangXCoordinates();
        int y = team.getMaSangYCoordinate();

        Map<Coordinate, Piece> pieces = super.create(team);
        pieces.put(new Coordinate(x.get(0), y), new Piece(team, PieceType.상));
        pieces.put(new Coordinate(x.get(1), y), new Piece(team, PieceType.마));
        pieces.put(new Coordinate(x.get(2), y), new Piece(team, PieceType.상));
        pieces.put(new Coordinate(x.get(3), y), new Piece(team, PieceType.마));
        return pieces;
    }
}
