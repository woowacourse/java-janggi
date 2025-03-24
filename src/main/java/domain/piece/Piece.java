package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.PieceType;

public abstract class Piece {
    private final Country country;
    private final PieceType pieceType;

    protected Piece(Country country, PieceType pieceType) {
        this.country = country;
        this.pieceType = pieceType;
    }

    public abstract void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to);

    protected void validateTarget(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        if (!board.isOccupied(to)) {
            return;
        }

        Piece currPiece = board.findPieceByCoordinate(from);
        Piece targetPiece = board.findPieceByCoordinate(to);
        if (Country.isSameContry(currPiece, targetPiece)) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
    }


    public Country getCountry() {
        return country;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
