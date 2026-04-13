package domain;

import java.util.Arrays;

public enum Formation {

    MA_SANG_MA_SANG("마상마상"),
    SANG_MA_SANG_MA("상마상마"),
    MA_SANG_SANG_MA("마상상마"),
    SANG_MA_MA_SANG("상마마상");

    private final String koreanName;

    Formation(String koreanName) {
        this.koreanName = koreanName;
    }

    public static Board from(String input, Board board, Team team) {
        Formation matchedFormation = Arrays.stream(values())
                .filter(formation -> formation.koreanName.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 진형 입력입니다."));

        if (matchedFormation == Formation.SANG_MA_SANG_MA) {
            return BoardFactory.setUpLeftElephantFormation(board.getBoard(), team);
        }
        if (matchedFormation == Formation.MA_SANG_MA_SANG) {
            return BoardFactory.setUpRightElephantFormation(board.getBoard(), team);
        }
        if (matchedFormation == Formation.MA_SANG_SANG_MA) {
            return BoardFactory.setUpInnerElephantFormation(board.getBoard(), team);
        }
        if (matchedFormation == Formation.SANG_MA_MA_SANG) {
            return BoardFactory.setUpOuterElephantFormation(board.getBoard(), team);
        }

        return board;
    }
}
