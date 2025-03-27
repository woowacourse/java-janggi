package view;

import java.util.Scanner;
import location.Position;

public class InputView {

    public static Position requestMoveStartPosition() {
        System.out.println("움직일 말을 알려주세요.");
        Scanner scanner = new Scanner(System.in);
        String from = scanner.nextLine();
        String[] s = from.split(" ");
        return new Position(
                Integer.parseInt(s[0]),
                Integer.parseInt(s[1])
        );
    }

    public static Position requestMovementEndPosition() {
        System.out.println("도착지를 알려주세요.");
        Scanner scanner = new Scanner(System.in);
        String to = scanner.nextLine();
        String[] s = to.split(" ");
        return new Position(
                Integer.parseInt(s[0]),
                Integer.parseInt(s[1])
        );
    }
}
