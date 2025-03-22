package janggi.domain.board;

import janggi.domain.piece.PieceType;
import java.util.Arrays;
import java.util.List;

public enum BoardSetup {
    INNER_ELEPHANT(1,
            List.of(PieceType.HORSE, PieceType.ELEPHANT),
            List.of(PieceType.ELEPHANT, PieceType.HORSE)
    ),

    OUTER_ELEPHANT(2,
            List.of(PieceType.ELEPHANT, PieceType.HORSE),
            List.of(PieceType.HORSE, PieceType.ELEPHANT)
    ),

    RIGHT_ELEPHANT(3,
            List.of(PieceType.HORSE, PieceType.ELEPHANT),
            List.of(PieceType.HORSE, PieceType.ELEPHANT)
    ),

    LEFT_ELEPHANT(4,
            List.of(PieceType.ELEPHANT, PieceType.HORSE),
            List.of(PieceType.ELEPHANT, PieceType.HORSE)
    ),
    ;

    private final int settingNumber;
    private final List<PieceType> leftSetup;
    private final List<PieceType> rightSetup;

    BoardSetup(int settingNumber, List<PieceType> leftSetup, List<PieceType> rightSetup) {
        this.settingNumber = settingNumber;
        this.leftSetup = leftSetup;
        this.rightSetup = rightSetup;
    }

    public static BoardSetup from(int settingNumber) {
        return Arrays.stream(BoardSetup.values())
                .filter(setup -> setup.settingNumber == settingNumber)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 숫자에 해당하는 세팅이 없습니다."));
    }

    public List<PieceType> getLeftSetup() {
        return leftSetup;
    }

    public List<PieceType> getRightSetup() {
        return rightSetup;
    }
}
    
