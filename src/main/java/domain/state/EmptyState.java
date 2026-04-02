package domain.state;

import domain.CountryType;
import domain.piece.Piece;
import domain.piece.PieceInfo;
import domain.piece.PieceType;

public class EmptyState implements State {
    private static final String NOT_FOUNT_PIECE_FROM_POSITION = "[ERROR] 해당 좌표에 기물이 존재하지 않습니다.";

    @Override
    public EmptyState copy() {
        return new EmptyState();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Piece getPiece() {
        throw new IllegalArgumentException(NOT_FOUNT_PIECE_FROM_POSITION);
    }

    @Override
    public PieceInfo getPieceInfo() {
        throw new IllegalArgumentException(NOT_FOUNT_PIECE_FROM_POSITION);
    }

    @Override
    public PieceType getPieceType() {
        throw new IllegalArgumentException(NOT_FOUNT_PIECE_FROM_POSITION);
    }

    @Override
    public double getPieceScore() {
        throw new IllegalArgumentException(NOT_FOUNT_PIECE_FROM_POSITION);
    }

    @Override
    public CountryType getPieceCountryType() {
        throw new IllegalArgumentException(NOT_FOUNT_PIECE_FROM_POSITION);
    }
}
