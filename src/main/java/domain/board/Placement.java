package domain.board;

import domain.piece.PieceType;

public enum Placement {

    INNER_ELEPHANT("마상상마", PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE),
    OUTER_ELEPHANT("상마마상", PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT),
    RIGHT_ELEPHANT("마상마상", PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT),
    LEFT_ELEPHANT("상마상마", PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE);

    private final String name;
    private final PieceType columnTwoType;
    private final PieceType columnThreeType;
    private final PieceType columnSevenType;
    private final PieceType columnEightType;

    Placement(String name, PieceType columnTwoType, PieceType columnThreeType, PieceType columnSevenType, PieceType columnEightType) {
        this.name = name;
        this.columnTwoType = columnTwoType;
        this.columnThreeType = columnThreeType;
        this.columnSevenType = columnSevenType;
        this.columnEightType = columnEightType;
    }

    public PieceType getColumnTwoType() {
        return columnTwoType;
    }

    public PieceType getColumnThreeType() {
        return columnThreeType;
    }

    public PieceType getColumnSevenType() {
        return columnSevenType;
    }

    public PieceType getColumnEightType() {
        return columnEightType;
    }
}
