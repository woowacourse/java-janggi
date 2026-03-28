package view;

import domain.game.Team;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputView {
    private static final int MOVE_INPUT_COUNT = 4;
    private final Scanner scanner = new Scanner(System.in);

    public Integer initialFormation(Team team) {
        System.out.println(team + " 진영 배치 전략을 입력 하세요.\n1. 왼상\n2. 오른상\n3. 원앙마\n4. 양귀마 ");
        try {
            int parseNumber = Integer.parseInt(scanner.nextLine());
            return validRange(parseNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 입력해주세요.");
        }

    }

    private int validRange(int number) {
        if (number < 1 || number > 4) {
            throw new IllegalArgumentException("1 ~ 4 사이의 숫자로 입력해주세요.");
        }
        return number;

    }

    public List<String> askMovePiecePoisiton(Team team) {
        System.out.println(team + "의 차례입니다. 움직일 기물의 위치와 이동할 위치를 행과 열 순서대로 입력하세요. ( 예: 2,5,4,3 )");
        List<String> positions = Arrays.stream(scanner.nextLine().split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        validatePositionFormat(positions);
        return positions;
    }

    private void validatePositionFormat(List<String> positions) {
        if (positions.size() != MOVE_INPUT_COUNT) {
            throw new IllegalArgumentException("예시와 똑같은 형식으로 입력해주세요. (예: 2,5,4,3)");
        }
    }
}
