package domain.piece;

import domain.Path;
import domain.Position;
import domain.country.CountryType;
import domain.strategy.MoveStrategy;

public record Piece(PieceInfo pieceInfo) {
    public Path path(Position from, Position to) {
        MoveStrategy moveStrategy = pieceInfo.pieceType().getMoveStrategy();
        return moveStrategy.path(from, to);
    }

    public void validateMove(PieceInfos pathPieceInfos, Position from, Position to) {
        MoveStrategy moveStrategy = pieceInfo.pieceType().getMoveStrategy();
        moveStrategy.validateMove(pathPieceInfos, from, to);
    }

    public PieceInfo getPieceInfo() {
        return pieceInfo;
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
