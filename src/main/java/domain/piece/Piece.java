package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import java.util.List;

abstract public class Piece {
    Country country;
    PieceType type;

    public Piece(Country country, PieceType type) {
        this.country = country;
        this.type = type;
    }

    abstract public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate, JanggiBoard janggiBoard);

    public Country getCountry(){
        return this.country;
    }

    public boolean isPho(){
        return false;
    }
}
