package domain.strategy;

import domain.piece.Blank;
import domain.piece.Piece;
import domain.position.Position;
import domain.PieceProvider;

import java.util.List;

public interface Strategy {
    List<Position> getMoveCandidates(Position from, PieceProvider board);
}
