package janggi.domain.strategy;

import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Piece;
import java.util.Map;

public class LoadStrategy implements InitializeStrategy {
    private final Map<Position, Piece> loadedPieces;

    public LoadStrategy(Map<Position, Piece> loadedPieces) {
        this.loadedPieces = loadedPieces;
    }

    @Override
    public void basicSetting(Map<Position, Space> blankBoard) {
        blankBoard.putAll(loadedPieces);
    }
}
