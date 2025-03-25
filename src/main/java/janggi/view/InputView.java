package janggi.view;

import janggi.domain.Player;
import janggi.domain.Position;
import janggi.domain.SetupType;
import janggi.domain.Team;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readCommand() {
        System.out.println("명령어를 입력하세요: 이동=move, 종료=end");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        return input.strip();
    }

    private static void validateEmptyInput(final String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("빈 입력입니다.");
        }
    }

    public Position readStartPosition() {
        System.out.println("움직일 기물을 선택하세요(행 열)");
        return readPosition();
    }

    public Position readMovePosition() {
        System.out.println("이동할 칸을 선택하세요(행 열)");
        return readPosition();
    }

    private Position readPosition() {
        try {
            String input = scanner.nextLine();
            validateEmptyInput(input);
            return parsePosition(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    private Position parsePosition(final String input) {
        String[] splitInput = input.split(" ");
        validateSplitSize(splitInput);
        int row = Integer.parseInt(splitInput[0]);
        int column = Integer.parseInt(splitInput[1]);
        return Position.of(row, column);
    }

    private void validateSplitSize(final String[] splitInput) {
        if (splitInput.length != 2) {
            throw new IllegalArgumentException("행 열 숫자 2개를 입력해주세요.");
        }
    }

    public Player readPlayer(final Team team) {
        System.out.println(TeamName.getCountryName(team) + " 팀에 참가할 플레이어 이름을 입력해주세요.");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        return new Player(input, team);
    }

    public SetupType readSetupType(final Team team) {
        try {
            System.out.println(TeamName.getCountryName(team) + " 팀의 사용하실 상차림 옵션을 선택하세요");
            System.out.println("""
                    1. 왼상차림(차 상 마 사 궁 사 상 마 차): 두 상이 모두 마의 왼쪽(대국자 기준)에 배치된다.
                    2. 오른상차림(차 마 상 사 궁 사 마 상 차): 두 상이 모두 마의 오른쪽에 배치된다.
                    3. 안상차림(차 마 상 사 궁 사 상 마 차): 두 상이 모두 사의 옆(궁에서 가까운 위치)에 배치된다.
                    4. 바깥상차림(차 상 마 사 궁 사 마 상 차): 두 상이 모두 차의 옆(궁에서 먼 위치)에 배치된다.""");
            String input = scanner.nextLine();
            validateEmptyInput(input);
            int optionInput = Integer.parseInt(input);
            return SetupType.findSetupType(optionInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    public void close() {
        scanner.close();
    }
}
