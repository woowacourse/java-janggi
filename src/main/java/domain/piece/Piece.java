package domain.piece;

import domain.Path;
import domain.Position;
import domain.country.CountryType;
import domain.strategy.MoveStrategy;

public record Piece(PieceInfo pieceInfo, MoveStrategy moveStrategy) {
    public Path path(Position from, Position to) {
        return moveStrategy.path(from, to);
    }

    public void validateMove(PieceInfos pathPieceInfos, Position from, Position to) {
        moveStrategy.validateMove(pathPieceInfos, from, to);
    }

    public PieceType getPieceType() {
        return pieceInfo.pieceType();
    }

    public CountryType getPieceCountryType() {
        return pieceInfo.countryType();
    }

    public double getPieceScore() {
        return pieceInfo.pieceType().getScore();
    }
}
