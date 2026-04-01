package view;

public enum PieceColor {
    CHO("\u001B[32m"),
    HAN("\u001B[31m"),
    NONE("\u001B[0m");

    private final String colorCode;

    PieceColor(String colorCode) {
        this.colorCode = colorCode;
    }

    public static String getColorCode(String name) {
        for (PieceColor pieceColor : values()) {
            if (pieceColor.name().equals(name)) {
                return pieceColor.colorCode;
            }
        }

        return NONE.colorCode;
    }
}
