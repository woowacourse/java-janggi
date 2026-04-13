package persistence.mapper;

import domain.Piece;
import domain.PieceProperty;
import domain.Position;
import domain.strategy.MoveStrategy;
import factory.MoveStrategyFactory;
import persistence.entity.PieceState;

public final class PieceStateMapper {

    public PieceState mapFrom(PieceProperty pieceProperty, Position position) {
        return new PieceState(pieceProperty, position);
    }

    public Piece mapToPiece(PieceState pieceState) {
        MoveStrategyFactory moveStrategyFactory = new MoveStrategyFactory();
        MoveStrategy moveStrategy = moveStrategyFactory.createMoveStrategy(pieceState.pieceProperty());
        return Piece.of(pieceState.pieceProperty(), moveStrategy);
    }
}
