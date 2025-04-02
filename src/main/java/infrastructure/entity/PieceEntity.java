package infrastructure.entity;

import domain.piece.coordiante.Coordinate;
import domain.piece.Country;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;
import java.util.Map;

public class PieceEntity {

    private final Long id;
    private final int x;
    private final int y;
    private final String pieceName;
    private final String country;

    public PieceEntity(
            Long id,
            String pieceName,
            int x,
            int y,
            String country
    ) {
        this.id = id;
        this.pieceName = pieceName;
        this.x = x;
        this.y = y;
        this.country = country;
    }

    public PieceEntity(String pieceName, int x, int y, String country) {
        this(null, pieceName, x, y, country);
    }

    public static List<PieceEntity> from(Map<Coordinate, Piece> pieces) {
        return pieces.entrySet().stream()
                .map(entry -> new PieceEntity(
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
