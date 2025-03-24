package domain.piece.state;

import domain.JanggiPosition;
import domain.pattern.Path;
import domain.pattern.Pattern;
import domain.piece.Piece;
import java.util.List;

public interface PieceState {
    List<Pattern> findMovablePath(Path path, JanggiPosition beforePosition, JanggiPosition afterPosition);

    void validateMove(List<Piece> hurdlePieces);

    PieceState captured();

    PieceState updateState();
}
