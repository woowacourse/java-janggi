package domain.piece.state;

import domain.JanggiPosition;
import domain.pattern.Path;
import domain.pattern.Pattern;
import java.util.List;

public interface PieceState {
    List<Pattern> findMovablePath(Path path, JanggiPosition beforePosition, JanggiPosition afterPosition);

    PieceState captured();

    PieceState updateState();
}
