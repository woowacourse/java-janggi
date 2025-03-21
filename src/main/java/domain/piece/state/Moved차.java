package domain.piece.state;

import domain.JanggiPosition;
import domain.pattern.Path;
import domain.pattern.Pattern;
import java.util.List;

public class Moved차 extends ContinuousPiece {
    @Override
    public List<Pattern> findMovablePath(Path path, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        return super.findMovablePath(path, beforePosition, afterPosition);
    }

    @Override
    public PieceState updateState() {
        return new Moved차() {
        };
    }
}
