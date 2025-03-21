package janggi.view;

import janggi.setting.PieceAssignType;
import java.util.Arrays;

public enum PieceAssignTypeAnswer {
    ONE("1", PieceAssignType.LEFT_TOP),
    TWO("2", PieceAssignType.RIGHT_TOP),
    THREE("3", PieceAssignType.IN_TOP),
    FOUR("4", PieceAssignType.OUT_TOP);

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
