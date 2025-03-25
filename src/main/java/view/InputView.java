package view;

import domain.Team;
import domain.player.Player;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public List<String> readPlayerNames() {
        System.out.println("플레이어 두 명의 이름을 입력하시오. (플레이어1, 플레이어2)");
        String input = scanner.nextLine();
        return Arrays.asList(input.split(","));
    }

    public List<Integer> readMovePiecePosition() {
        System.out.println("움직일 기물의 행과 열을 입력하시오. 예) 4,1");
        return readPosition();
    }

    public List<Integer> readTargetPosition() {
        System.out.println("이동할 위치의 행과 열을 입력하시오. 예) 4,2");
        return readPosition();
    }

    private List<Integer> readPosition() {
        String input = scanner.nextLine();
        String[] positions = input.split(",");
        int row = Integer.parseInt(positions[0]);
        int column = Integer.parseInt(positions[1]);
        return List.of(row, column);
    }

    public String readCommand(Player player) {
        System.out.printf("%s의 차례입니다. 게임을 계속하려면 y, 종료하려면 n을 입력하시오.%n", convertToCountry(player.getTeam()));
        return scanner.nextLine();
    }

    private String convertToCountry(Team team) {
        return switch (team) {
            case Team.HAN -> "한나라";
            case Team.CHO -> "초나라";
        };
    }
}
