package model.pieces;

import java.util.Objects;

import model.board.Board;
import model.board.Country;
import model.move.Move;

public class Piece {
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
        return pieceType.rule().matches(move, board, country);
    }

    public int score() {
        return pieceType().score();
    }

    public Country country() {
        return country;
    }

    public PieceType pieceType() {
        return pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Piece piece)) {
            return false;
        }
        return country == piece.country
                && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, pieceType);
    }
}
