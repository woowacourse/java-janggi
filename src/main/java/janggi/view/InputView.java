package janggi.view;

import janggi.controller.dto.PositionRequest;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class InputView {
    private static final String QUIT_COMMAND = "q";
    private final Scanner scanner = new Scanner(System.in);

    public int readMenuOption() {
        System.out.println("1. 새로 시작 2. 불러 오기 3. 나가기");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public Optional<String> readGameName() {
        System.out.println("이 게임의 이름을 입력해주세요. (예: 장기1 / q: 취소)");
        String gameName = scanner.nextLine().trim();

        if (isQuit(gameName)) {
            return Optional.empty();
        }
        return Optional.of(gameName);
    }

    public Optional<String> readGameName(List<String> gameNames) {
        System.out.println("불러올 게임을 선택해주세요. (예: 장기1 / q: 취소)");

        for (int i = 0; i < gameNames.size(); i++) {
            System.out.println((i + 1) + ". " + gameNames);
        }
        String input = scanner.nextLine().trim();
        if (isQuit(input)) {
            return Optional.empty();
        }
        return Optional.of(input);
    }

    public Optional<Integer> readFormationChoice(String camp) {
        System.out.println(camp + "의 마상 배치를 선택해주세요. (예: 1 / q: 취소)");
        System.out.println("1. 마상상마 2. 마상마상 3. 상마마상 4. 상마상마");
        String input = scanner.nextLine().trim();
        if (isQuit(input)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(input));
    }

    public int readCommand() {
        System.out.println("1. 항복 2. 무승부 3. 계속");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public boolean confirmDraw() {
        System.out.println("상대방이 무승부를 제안합니다. 수락하시겠습니까? (y/n)");
        String input = scanner.nextLine().trim();
        return input.equals("y");
    }

    public Optional<PositionRequest> readPieceSelection() {
        System.out.println("기물을 선택해주세요. (x y / q: 취소)");
        String input = scanner.nextLine().trim();
        if (isQuit(input)) {
            return Optional.empty();
        }
        return Optional.of(PositionRequest.from(input));
    }

    public Optional<PositionRequest> readMoveDestination() {
        System.out.println("이동할 위치를 입력해주세요. (x y / q: 취소)");
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
