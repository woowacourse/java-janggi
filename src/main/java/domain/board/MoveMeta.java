package domain.board;

public record MoveMeta(boolean sourceInPalace, boolean destinationInPalace, boolean diagonalMove) {

    public static MoveMeta empty() {
        return new MoveMeta(false, false, false);
    }

    public boolean isPalaceMove() {
        return sourceInPalace && destinationInPalace;
    }
}

