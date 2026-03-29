package janggi.view;

import janggi.view.dto.PositionRequest;

import java.util.Optional;
import java.util.Scanner;

public class InputView {
    private static final String QUIT_COMMAND = "q";
    private final Scanner scanner = new Scanner(System.in);

    public int readFormationChoice(int playerNumber) {
        System.out.println(playerNumber + "P 마상 배치를 선택해주세요.");
        System.out.println("1. 마상상마 2. 마상마상 3. 상마마상 4. 상마상마");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public Optional<PositionRequest> readPieceSelection() {
        System.out.println("기물을 선택해주세요. (y x / q: 게임 종료)");
        String input = scanner.nextLine().trim();
        if (isQuit(input)) {
            System.out.println("게임이 종료되었습니다.");
            return Optional.empty();
        }
        return Optional.of(PositionRequest.from(input));
    }

    public Optional<PositionRequest> readMoveDestination() {
        System.out.println("이동할 위치를 입력해주세요. (y x / q: 취소)");
        String input = scanner.nextLine().trim();
        if (isQuit(input)) {
            return Optional.empty();
        }
        return Optional.of(PositionRequest.from(input));
    }

    private boolean isQuit(String input) {
        return QUIT_COMMAND.equals(input);
    }
}
