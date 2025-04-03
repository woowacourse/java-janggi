package domain.piece.rule;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Optional;

public interface Rule {

    void validate(List<Position> path, List<Piece> piecesInPath, Piece selectedPiece, Optional<Piece> targetPiece);
}
