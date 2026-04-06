package janggi.view;

import janggi.domain.dto.MoveCommand;
import janggi.domain.piece.Team;

import java.util.*;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public int readTurnBehavior(Team team) {
        System.out.println(getTeamName(team) + "의 턴입니다.");
        System.out.println("1. 말 이동 / 2. 턴 넘기기 / 3. 기권 중 하나의 옵션을 선택해주세요. \n(숫자만 입력, 예: 1)");
        String input = scanner.nextLine();
        int number;

        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(input + "는 정수가 아닙니다.");
        }

        if (!List.of(1, 2, 3).contains(number)){
            throw new IllegalArgumentException(input + "이/가 아닌 1 2 3 사이의 숫자가 입력되어야합니다.");
        }

        return number;
    }

    public MoveCommand readMovePositions(Team team) {
        System.out.println("장기를 이동시킬 좌표들을 이동할 행 이동할 열 도착할 행 도착할 열 형태로 입력해 주세요.");
        System.out.println("ex 1 1 2 2");

        String rawInput = scanner.nextLine();
        int[] numbers = Arrays.stream(rawInput.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        return MoveCommand.from(numbers);
    }

    private String getTeamName(Team team) {
        Map<Team, String> names = new HashMap<>(){
            {
                put(Team.CHO, "초");
                put(Team.HAN, "한");
            }
        };

        return names.get(team);
    }
}
