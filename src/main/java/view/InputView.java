package view;

import piece.Position;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public Position readPresentPick() {
        System.out.println("초나라 턴 입니다. 움직이고 싶은 말의 위치를 입력해주세요.");

        String inputPosition = readLine().trim();

        String[] split = inputPosition.split(",");
        return new Position(parseInt(split[0]), parseInt(split[1]));
    }

    public Position readFuturePick() {
        System.out.println("해당 말을 이동시킬 위치를 입려해 주세요.");

        String inputPosition = readLine().trim();

        String[] split = inputPosition.split(",");
        return new Position(parseInt(split[0]), parseInt(split[1]));
    }

    private int parseInt(final String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 좌표는 숫자만 입력 할 수 있습니다.");
        }
    }

    private String readLine() {
        return scanner.nextLine();
    }
}
