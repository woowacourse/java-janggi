package janggi.view;

import janggi.view.dto.GameRoom;
import janggi.view.dto.PositionRequest;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class InputView {
    private static final String QUIT_COMMAND = "q";
    private static final String YES_COMMAND = "y";
    private final Scanner scanner = new Scanner(System.in);

    public Long readRoomNumber(List<GameRoom> gameRooms) {
        if (gameRooms.isEmpty()) {
            return 0L;
        }
        for (GameRoom gameRoom : gameRooms) {
            System.out.printf("%d번방 | [%s차례] 진행여부: %s%n",
                    gameRoom.getId(), gameRoom.getTurn(), isOnGoing(gameRoom.isOnGoing()));
        }
        System.out.println("어느 방에 입장하시겠습니까? 새 게임 참여는 0을 입력해주세요.");
        return Long.parseLong(scanner.nextLine().trim());
    }

    private String isOnGoing(boolean isOnGoing) {
        if (isOnGoing) {
            return "진행 중";
        }
        return "게임 종료";
    }

    public int readFormationChoice(int playerNumber) {
        System.out.println(playerNumber + "P 마상 배치를 선택해주세요.");
        System.out.println("1. 마상상마 2. 마상마상 3. 상마마상 4. 상마상마");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public PositionRequest readPieceSelection() {
        System.out.println("기물을 선택해주세요. (행 열 형태로 입력해주세요. / 예시: 3 0 / q: 항복 / d: 무승부 요청)");
        String input = scanner.nextLine().trim();
        return PositionRequest.from(input);
    }

    public boolean readAcceptDrawRequest() {
        System.out.println("무승부 제안을 받아들이시겠습니까? 받아들일 경우 점수로 승/무/패가 계산됩니다. y or n로 입력");
        String input = scanner.nextLine().trim();
        return isYes(input);
    }

    public Optional<PositionRequest> readMoveDestination() {
        System.out.println("이동할 위치를 입력해주세요. (행 열 형태로 입력해주세요. / 예시: 3 0 / q: 뒤로 가기)");
        String input = scanner.nextLine().trim();
        if (isQuit(input)) {
            return Optional.empty();
        }
        return Optional.of(PositionRequest.from(input));
    }

    private boolean isQuit(String input) {
        return QUIT_COMMAND.equals(input);
    }

    private boolean isYes(String input) {
        return YES_COMMAND.equals(input);
    }

}
