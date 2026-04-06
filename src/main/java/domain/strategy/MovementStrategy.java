package domain.strategy;

import domain.board.PathPieces;
import domain.position.Path;

public interface MovementStrategy {
    boolean isValidPath(Path path, PathPieces pathPieces);
}
