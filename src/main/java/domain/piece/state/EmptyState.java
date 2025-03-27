package domain.piece.state;

import domain.JanggiPosition;
import domain.pattern.Path;
import domain.pattern.Pattern;
import domain.piece.Piece;
import java.util.List;

public class EmptyState implements PieceState {
    @Override
    public List<Pattern> findMovablePath(Path path, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        throw new UnsupportedOperationException("비어있는 칸입니다.");
    }

    @Override
    public void validateMove(List<Piece> hurdlePieces) {
        throw new UnsupportedOperationException("비어있는 칸입니다.");
    }

    @Override
    public PieceState captured() {
        return this;
    }

    @Override
    public PieceState updateState() {
        throw new UnsupportedOperationException("비어있는 칸입니다.");
    }
}
