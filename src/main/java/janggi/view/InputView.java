package janggi.view;

import janggi.domain.JanggiGame;
import janggi.domain.dto.MoveCommand;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public MoveCommand readMovePositions() {

        System.out.println("장기를 이동시킬 좌표들을 이동할행 이동할열 도착할행 도착할열 형태로 입력해 주세요.");
        System.out.println("ex 1 1 2 2");

        String rawInput = scanner.nextLine();
        int[] numbers = Arrays.stream(rawInput.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        return MoveCommand.from(numbers);
    }

    public int readGameChoice(List<JanggiGame> playingGames) {
        System.out.println("진행 중인 게임이 있습니다.");
        for (int i = 0; i < playingGames.size(); i++) {
            JanggiGame game = playingGames.get(i);
            System.out.println((i + 1) + ". 게임 " + game.findGameId()
                    + " (현재 턴: " + game.findCurrentTeam() + ")");
        }
        System.out.println((playingGames.size() + 1) + ". 새 게임 시작");
        System.out.println("번호를 입력해 주세요.");

        return Integer.parseInt(scanner.nextLine());
    }

}
