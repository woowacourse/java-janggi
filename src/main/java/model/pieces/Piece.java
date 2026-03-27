package model.pieces;

import java.util.Objects;

import model.board.Board;
import model.board.Country;
import model.move.Move;

public abstract class Piece {
    private final Country country;
    private final PieceType pieceType;

    public Piece(Country country, PieceType pieceType) {
        this.country = country;
        this.pieceType = pieceType;
    }

    public String mark() {
        return country().color() + pieceType.symbol() + Country.RESET;
    }

    public boolean canMove(Move move, Board board) {
        return pieceType.rule().matches(move, board);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return country == piece.country;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(country);
    }

    public Country country() {
        return country;
    }

    public PieceType pieceType() {
        return pieceType;
    }
}
