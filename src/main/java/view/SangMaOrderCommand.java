package view;

import domain.PieceType;
import java.util.Arrays;
import java.util.List;

public enum SangMaOrderCommand {

    SANG_MA_SANG_MA("1", List.of(PieceType.SANG, PieceType.MA, PieceType.SANG, PieceType.MA)),
    SANG_MA_MA_SANG("2", List.of(PieceType.SANG, PieceType.MA, PieceType.MA, PieceType.SANG)),
    MA_SANG_SANG_MA("3", List.of(PieceType.MA, PieceType.SANG, PieceType.SANG, PieceType.MA)),
    MA_SANG_MA_SANG("4", List.of(PieceType.MA, PieceType.SANG, PieceType.MA, PieceType.SANG)),
    ;

    private final String input;
    private final List<PieceType> pieceTypes;

    SangMaOrderCommand(String input, List<PieceType> pieceTypes) {
        this.input = input;
        this.pieceTypes = pieceTypes;
    }

    public static SangMaOrderCommand from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.input.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 올바른 상마 순서 커맨드를 입력해주세요."));
    }

    public List<PieceType> getPieceTypes() {
        return pieceTypes;
    }
}
