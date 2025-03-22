package board;

import board.create.strategy.BoardCreateStrategy;
import coordinate.Coordinate;
import java.util.HashMap;
import java.util.Map;
import piece.Piece;
import team.Team;

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
        BoardCreateStrategy strategy = new BoardCreateStrategy() {
            @Override
            public Map<Coordinate, Piece> create(Team team) {
                return pieces;
            }
        };

        return Board.create(strategy, strategy);
    }
}
