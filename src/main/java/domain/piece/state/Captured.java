package domain.piece.state;

import domain.JanggiPosition;
import domain.pattern.Path;
import domain.pattern.Pattern;
import java.util.List;

public class Captured implements PieceState {

    @Override
    public List<Pattern> findMovablePath(Path path, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        throw new UnsupportedOperationException("잡힌 기물입니다.");
    }

    @Override
    public PieceState captured() {
        throw new UnsupportedOperationException("잡힌 기물입니다.");
    }

    @Override
    public PieceState updateState() {
        throw new UnsupportedOperationException("잡힌 기물입니다.");
    }
}
