package janggi.domain.board;

import java.util.List;

public enum BoardFormation {
    MA_SANG_MA_SANG(1, "마상마상", List.of(2, 7), List.of(3, 8)),
    MA_SANG_SANG_MA(2, "마상상마", List.of(2, 8), List.of(3, 7)),
    SANG_MA_SANG_MA(3, "상마상마", List.of(3, 8), List.of(2, 7)),
    SANG_MA_MA_SANG(4, "상마마상", List.of(3, 7), List.of(2, 8));

    private final int choice;
    private final String name;
    private final List<Integer> maXPositions;
    private final List<Integer> sangXPositions;

    BoardFormation(int choice, String name, List<Integer> maXPositions, List<Integer> sangXPositions) {
        this.choice = choice;
        this.name = name;
        this.maXPositions = maXPositions;
        this.sangXPositions = sangXPositions;
    }

    public static BoardFormation selectByChoice(int choice) {
        if (choice == 1) {
            return MA_SANG_MA_SANG;
        }
        if (choice == 2) {
            return MA_SANG_SANG_MA;
        }
        if (choice == 3) {
            return SANG_MA_SANG_MA;
        }
        if (choice == 4) {
            return SANG_MA_MA_SANG;
        }
        throw new IllegalArgumentException("[ERROR] 잘못된 번호를 입력하셨습니다.");
    }

    public int getChoice() {
        return choice;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getMaXPositions() {
        return maXPositions;
    }

    public List<Integer> getSangXPositions() {
        return sangXPositions;
    }
}
