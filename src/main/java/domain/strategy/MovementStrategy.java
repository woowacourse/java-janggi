package domain.strategy;

import domain.board.PathPieces;

public interface MovementStrategy {
    boolean validatePath(PathPieces pathPieces);
}
