package janggi.view.answer;

import janggi.rule.PieceAssignType;
import java.util.Arrays;

public enum PieceAssignTypeAnswer {
    LEFT_SANG("1", PieceAssignType.LEFT_SANG),
    RIGHT_SANG("2", PieceAssignType.RIGHT_SANG),
    IN_SANG("3", PieceAssignType.IN_SANG),
    OUT_SANG("4", PieceAssignType.OUT_SANG);

    private final String command;
    private final PieceAssignType pieceAssignType;

    PieceAssignTypeAnswer(final String command, final PieceAssignType pieceAssignType) {
        this.command = command;
        this.pieceAssignType = pieceAssignType;
    }

    public static PieceAssignType parse(final String number) {
        return Arrays.stream(PieceAssignTypeAnswer.values())
                .filter(answer -> answer.command.equals(number))
                .findFirst()
                .map(value -> value.pieceAssignType)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 적절하지 않은 입력값입니다."));
    }
}
