package domain.state;

import domain.CountryType;
import domain.piece.Piece;
import domain.piece.PieceInfo;
import domain.piece.PieceType;

public record FullState(Piece piece) implements State {
    @Override
    public FullState copy() {
        return new FullState(piece);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }

    @Override
    public PieceInfo getPieceInfo() {
        return piece.getPieceInfo();
    }

    @Override
    public PieceType getPieceType() {
        return piece.getPieceType();
    }

    @Override
    public double getPieceScore() {
        return piece.getPieceScore();
    }

    @Override
    public CountryType getPieceCountryType() {
        return piece.getPieceCountryType();
    }
}
