package janggi.view;

import janggi.setting.PieceAssignType;
import java.util.Arrays;

public enum PieceAssignTypeAnswer {
    ONE("1", PieceAssignType.LEFT_SANG),
    TWO("2", PieceAssignType.RIGHT_SANG),
    THREE("3", PieceAssignType.IN_SANG),
    FOUR("4", PieceAssignType.OUT_SANG);

    private final String command;
    private final PieceAssignType pieceAssignType;

    PieceAssignTypeAnswer(final String command, final PieceAssignType pieceAssignType) {
        this.command = command;
        this.pieceAssignType = pieceAssignType;
    }

    public static PieceAssignType from(final String number) {
        return Arrays.stream(PieceAssignTypeAnswer.values())
                .filter(command -> command.command.equals(number))
                .findFirst()
                .map(value -> value.pieceAssignType)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 적절하지 않은 입력값입니다."));
    }
}
