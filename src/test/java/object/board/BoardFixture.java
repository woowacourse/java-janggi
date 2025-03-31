package object.board;

import object.board.creator.TableSettingCreator;
import object.coordinate.Coordinate;
import java.util.HashMap;
import java.util.Map;
import object.piece.Piece;
import object.team.Country;

public class BoardFixture {

    private final Map<Coordinate, Piece> pieces;

    public BoardFixture() {
        pieces = new HashMap<>();
    }

    public BoardFixture addPiece(int x, int y, Piece piece) {
        pieces.put(new Coordinate(x, y), piece);
        return this;
    }

    public Board build() {
        TableSettingCreator creator = new TableSettingCreator() {
            @Override
            public Map<Coordinate, Piece> create(Country team) {
                return pieces;
            }
        };

        return Board.create(creator, creator);
    }
}
