package domain.constant;

import java.util.Arrays;
import java.util.List;

public enum MaSang {
    MA_SANG_SANG_MA(1, List.of(PieceType.MA, PieceType.SANG, PieceType.SANG, PieceType.MA)),
    MA_SANG_MA_SANG(2, List.of(PieceType.MA, PieceType.SANG, PieceType.MA, PieceType.SANG)),
    SANG_MA_SANG_MA(3, List.of(PieceType.SANG, PieceType.MA, PieceType.SANG, PieceType.MA)),
    SANG_MA_MA_SANG(4, List.of(PieceType.SANG, PieceType.MA, PieceType.MA, PieceType.SANG));

    private final int command;
    private final List<PieceType> pieceTypes;

    MaSang(int command, List<PieceType> pieceTypes) {
        this.command = command;
        this.pieceTypes = pieceTypes;
    }

    public static List<PieceType> getMaSangPosition(int command) {
        return Arrays.stream(values())
                .filter(maSang -> maSang.command == command)
                .findAny()
                .map(maSang -> maSang.pieceTypes)
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 입력입니다."));
    }
}
