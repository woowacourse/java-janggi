package view;

import domain.piece.Team;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readNewOrContinueGame() {
        System.out.println("새로운 게임을 하려면 new, 이어서 하려면 continue를 입력하시오.");
        return scanner.nextLine();
    }

    public String readNewRoomName() {
        System.out.println("생성할 장기 게임방의 이름을 입력하시오.");
        return scanner.nextLine();
    }

    public String readRoomName() {
        System.out.println("입장할 장기 게임방의 이름을 입력하시오.");
        return scanner.nextLine();
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

    public String readCommand(Team team) {
        System.out.printf("%s의 차례입니다. 게임을 계속하려면 go, 종료하려면 q, 점수를 확인하려면 s를 입력하시오.%n", convertToCountry(team));
        return scanner.nextLine();
    }

    private String convertToCountry(Team team) {
        return switch (team) {
            case Team.HAN -> "한나라";
            case Team.CHO -> "초나라";
        };
    }
}
