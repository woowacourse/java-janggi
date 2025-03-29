package view;

import java.util.Scanner;
import location.Position;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static AnswerType requestEndGame() {
        System.out.println("게임을 일시 종료하시겠습니까?(y, n)");
        String answer = SCANNER.nextLine();
        return InputParser.parseAnswerType(answer);
    }

    public static Position requestMoveStartPosition() {
        System.out.println("움직일 말을 알려주세요.");
        String from = SCANNER.nextLine();
        String[] s = from.split(" ");
        return new Position(
                Integer.parseInt(s[0]),
                Integer.parseInt(s[1])
        );
    }

    public static Position requestMovementEndPosition() {
        System.out.println("도착지를 알려주세요.");
        String to = SCANNER.nextLine();
        String[] s = to.split(" ");
        return new Position(
                Integer.parseInt(s[0]),
                Integer.parseInt(s[1])
        );
    }
}
