package domain.board.strategy;

import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.Map;

public interface InitialStrategy {

    void apply(Map<Position, Piece> pieceInitPlacements);
}
