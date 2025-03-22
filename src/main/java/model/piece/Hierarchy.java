package model.piece;

public interface Hierarchy {
    default boolean isCriticalPiece() {
        return false;
    }
}
