package view;

import java.util.Map;

public class InputParser {
    private static final Map<String, AnswerType> ANSWER_TYPE_PARSE_INFO = Map.of(
            "y", AnswerType.YES,
            "n", AnswerType.NO
    );

    public static AnswerType parseAnswerType(String input) {
        AnswerType answerType = ANSWER_TYPE_PARSE_INFO.getOrDefault(input, AnswerType.NONE);
        if (answerType.isInvalid()) {
            throw new IllegalArgumentException("[ERROR] 대답은 y 혹은 n으로 해주세요.");
        }
        return answerType;
    }
}
