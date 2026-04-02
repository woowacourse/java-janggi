package domain.strategy;

import domain.board.PathPieces;

public interface MovementStrategy {
    boolean isValidPath(PathPieces pathPieces);
}
