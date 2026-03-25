package domain.strategy;

import domain.board.PathPieces;

public interface MovementStrategy {
    public boolean validatePath(PathPieces pathPieces);
}
