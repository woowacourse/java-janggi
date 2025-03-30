package infrastructure.entity;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.Country;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;
import java.util.Map;

public class BoardEntity {

    private final Long id;
    private final String pieceName;
    private final int x;
    private final int y;
    private final String country;

    public BoardEntity(
            Long id,
            String pieceName,
            int x, int y,
            String country
    ) {
        this.id = id;
        this.pieceName = pieceName;
        this.x = x;
        this.y = y;
        this.country = country;
    }

    public static List<BoardEntity> from(Board board) {
        return board.getBoard().entrySet().stream()
                .map(entry -> new BoardEntity(
                        null,
                        entry.getValue().getType().name(),
                        entry.getKey().row(),
                        entry.getKey().col(),
                        entry.getValue().getCountry().name()
                ))
                .toList();
    }

    public Map.Entry<Coordinate, Piece> toDomain() {
        return Map.entry(
                new Coordinate(x, y),
                Piece.of(Country.valueOf(country), PieceType.valueOf(pieceName))
        );
    }

    public String getPieceName() {
        return pieceName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getCountry() {
        return country;
    }
}
