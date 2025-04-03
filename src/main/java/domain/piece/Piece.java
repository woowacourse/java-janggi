package domain.piece;

import domain.board.ReadableBoard;
import domain.piece.coordiante.Coordinate;
import domain.piece.jump.Pho;
import java.util.List;

public abstract class Piece {

    protected final Country country;
    protected final PieceType type;

    protected Piece(Country country, PieceType type) {
        this.country = country;
        this.type = type;
    }

    public static Piece of(Country country, PieceType type) {
        return switch (type) {
            case PHO -> new Pho(country);
            case SA -> new Sa(country);
            case GUNG -> new Gung(country);
            case BYEONG -> new Byeong(country);
            case MA -> new Ma(country);
            case SANG -> new Sang(country);
            case CHA -> new Cha(country);
        };
    }

    public abstract List<Coordinate> findAvailablePaths(Coordinate from, ReadableBoard readableBoard);

    public Country getCountry() {
        return this.country;
    }

    public PieceType getType() {
        return type;
    }

    public int getScore() {
        return type.getScore();
    }

    public boolean isHan() {
        return country.isHan();
    }

    public boolean isCho() {
        return country.isCho();
    }
}
