package janggi.view;

import janggi.player.Player;
import janggi.player.Team;
import janggi.view.command.Command;
import janggi.view.command.GameQuitCommand;
import janggi.view.command.GameSaveCommand;
import janggi.view.command.MoveCommand;

import java.util.List;
import java.util.Scanner;

public class InputView {

    public static final String QUIT = "Q";
    public static final String SAVE = "S";

    private final Scanner scanner = new Scanner(System.in);

    public StartOption inputStartOption() {
        System.out.print("""
                \s
                 1. 새 게임
                 2. 기존 게임
                 Q. 종료하기
                \s
                 >>>\s""");

        return StartOption.from(scanner.nextLine());
    }

    public int inputSelectedSavedGameId(final List<Integer> gameIds) {
        System.out.println("불러올 게임을 선택하세요 (예시) 1");

        for (final Integer gameId : gameIds) {
            System.out.println("저장된 게임 번호 " + gameId);
        }

        return Integer.parseInt(scanner.nextLine());
    }

    public Command inputCommand(final Player player) {
        System.out.println("종료: Q");
        System.out.println("저장: S");
        System.out.printf("%s, %s",
                processColor(player.getTeam(), player.getTeam().getDescription() + "나라"),
                "자신의 기물을 움직이세요.");
        System.out.println("이동: (예시) 1,1 2,1");
        System.out.print(">>> ");
        final String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase(QUIT)) {
            return new GameQuitCommand();
        }

        if (input.equalsIgnoreCase(SAVE)) {
            return new GameSaveCommand();
        }

        final String[] departureAndDestination = input.split(" ");
        if (departureAndDestination.length != 2) {
            throw new IllegalArgumentException("이동 명령은 출발 위치와 목적지 위치로 구성되어야 합니다. 예: '1,1 2,1'");
        }

        final String[] departurePosition = departureAndDestination[0].split(",");
        final String[] destinationPosition = departureAndDestination[1].split(",");
        if (departurePosition.length != 2 || destinationPosition.length != 2) {
            throw new IllegalArgumentException("위치는 행과 열로 구성되어야 합니다. 예: '1,1'");
        }

        return MoveCommand.of(
                departurePosition[0],
                departurePosition[1],
                destinationPosition[0],
                destinationPosition[1]
        );
    }

    private String processColor(final Team team, final String string) {
        if (team.isCho()) {
            return processGreenColorString(string);
        }
        return processRedColorString(string);
    }

    private String processRedColorString(final String string) {
        return "\u001B[31m" + string + "\u001B[0m";
    }

    private String processGreenColorString(final String string) {
        return "\u001B[32m" + string + "\u001B[0m";
    }
}
