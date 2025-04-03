package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.PieceType;

import java.util.Objects;

import static domain.JanggiBoard.*;

public abstract class Piece {
    private final Country country;
    private final PieceType pieceType;

    protected Piece(Country country, PieceType pieceType) {
        this.country = country;
        this.pieceType = pieceType;
    }

    public abstract void validatePieceMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to);

    public void validateDestination(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateCoordinate(from);
        validateCoordinate(to);
        validateTarget(board, from, to);
        validatePieceMove(board, from, to);
    }

    private void validateTarget(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        if (!board.isOccupied(to)) {
            return;
        }

        Piece currPiece = board.findPieceByCoordinate(from);
        Piece targetPiece = board.findPieceByCoordinate(to);
        if (Country.isSameCountry(currPiece, targetPiece)) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
    }

    private void validateCoordinate(JanggiCoordinate coordinate) {
        if (coordinate.row() < BOUNDARY_START || coordinate.row() > ROW_SIZE || coordinate.col() < BOUNDARY_START || coordinate.col() > COL_SIZE) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 좌표입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return country == piece.country && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, pieceType);
    }

    public Country getCountry() {
        return country;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public int getScore() {
        return this.pieceType.getScore();
    }
}
