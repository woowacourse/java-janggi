package domain.board;

import domain.piece.PieceType;

import java.util.Arrays;

public enum Placement {

    INNER_ELEPHANT(1, "마상상마", PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE),
    OUTER_ELEPHANT(2, "상마마상", PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT),
    RIGHT_ELEPHANT(3, "마상마상", PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT),
    LEFT_ELEPHANT(4, "상마상마", PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE);

    private final int code;
    private final String name;
    private final PieceType columnTwoType;
    private final PieceType columnThreeType;
    private final PieceType columnSevenType;
    private final PieceType columnEightType;

    Placement(
            int code,
            String name,
            PieceType columnTwoType,
            PieceType columnThreeType,
            PieceType columnSevenType,
            PieceType columnEightType
    ) {
        this.code = code;
        this.name = name;
        this.columnTwoType = columnTwoType;
        this.columnThreeType = columnThreeType;
        this.columnSevenType = columnSevenType;
        this.columnEightType = columnEightType;
    }

    public static Placement from(int code) {
        return Arrays.stream(values())
                .filter(placement -> placement.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 코드값 입니다."));
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
