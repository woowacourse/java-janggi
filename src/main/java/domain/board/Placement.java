package domain.board;

import domain.piece.PieceType;

import java.util.Arrays;

public enum Placement {

    INNER_ELEPHANT(1, PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE),
    OUTER_ELEPHANT(2, PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT),
    RIGHT_ELEPHANT(3, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT),
    LEFT_ELEPHANT(4, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE);

    private final int code;
    private final PieceType firstPieceType;
    private final PieceType secondPieceType;
    private final PieceType thirdPieceType;
    private final PieceType fourthPieceType;

    Placement(
            int code,
            PieceType firstPieceType,
            PieceType secondPieceType,
            PieceType thirdPieceType,
            PieceType fourthPieceType
    ) {
        this.code = code;
        this.firstPieceType = firstPieceType;
        this.secondPieceType = secondPieceType;
        this.thirdPieceType = thirdPieceType;
        this.fourthPieceType = fourthPieceType;
    }

    public static Placement from(int code) {
        return Arrays.stream(values())
                .filter(placement -> placement.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 코드값 입니다."));
    }

    public PieceType getFirstPieceType() {
        return firstPieceType;
    }

    public PieceType getSecondPieceType() {
        return secondPieceType;
    }

    public PieceType getThirdPieceType() {
        return thirdPieceType;
    }

    public PieceType getFourthPieceType() {
        return fourthPieceType;
    }
}
