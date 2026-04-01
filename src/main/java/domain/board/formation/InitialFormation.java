package domain.board.formation;

import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.Map;

public interface InitialFormation {

    void apply(Map<Position, Piece> pieceInitPlacements);
}
