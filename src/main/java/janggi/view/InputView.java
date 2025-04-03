package janggi.view;

import janggi.command.Command;
import janggi.command.MoveCommand;
import janggi.command.QuitCommand;
import janggi.command.SaveCommand;
import janggi.player.Player;
import janggi.player.Team;
import janggi.repository.dto.GameDto;

import java.util.List;
import java.util.Scanner;

public class InputView {

    public static final String QUIT = "Q";
    public static final String SAVE = "S";

    private final Scanner scanner = new Scanner(System.in);

    private static void displayInputPointer() {
        System.out.print(">>> ");
    }

    public StartOption inputStartOption() {
        System.out.print("""
                
                 1. 새 게임
                 2. 기존 게임
                 Q. 종료하기
                
                """);
        displayInputPointer();

        return StartOption.from(scanner.nextLine());
    }

    public int inputSelectedSavedGameId(final List<GameDto> games) {
        for (final GameDto game : games) {
            System.out.println();
            System.out.print("저장된 게임 번호 " + game.id());
            System.out.print(", 게임 시작 시간 " + game.startAt());
            System.out.print(", 최근 저장 시간 " + game.lastSavedAt());
            System.out.println();
        }

        System.out.println("불러올 게임의 번호를 입력하세요. (예시) 1");
        displayInputPointer();

        return Integer.parseInt(scanner.nextLine());
    }

    public Command inputCommand(final Player player) {
        System.out.println("종료: Q");
        System.out.println("저장: S");
        System.out.printf("%s, %s",
                processColor(player.getTeam(), player.getTeam().getDescription() + "나라"),
                "자신의 기물을 움직이세요.");
        System.out.println("이동: (예시) 1,1 2,1");
        displayInputPointer();
        final String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase(QUIT)) {
            return new QuitCommand();
        }

        if (input.equalsIgnoreCase(SAVE)) {
            return new SaveCommand();
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
