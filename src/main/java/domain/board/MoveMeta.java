package domain.board;

public record MoveMeta(boolean sourceInPalace, boolean destinationInPalace, boolean diagonalMove, boolean palaceDiagonalReachable) {

    public MoveMeta(boolean sourceInPalace, boolean destinationInPalace, boolean diagonalMove) {
        this(sourceInPalace, destinationInPalace, diagonalMove, true);
    }

    public static MoveMeta empty() {
        return new MoveMeta(false, false, false, false);
    }

    public boolean isPalaceMove() {
        return sourceInPalace && destinationInPalace;
    }
}
