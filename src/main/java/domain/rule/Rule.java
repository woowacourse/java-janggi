package domain.rule;

import domain.Side;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.List;
import java.util.Map;

public interface Rule {
    List<Position> getPossiblePositions(Side movingSide, Map<Position, Piece> pathPieces, List<Path> paths);
}
