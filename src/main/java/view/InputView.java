package view;

import static player.Nation.CHO;

import pieceProperty.Position;
import java.util.Scanner;
import player.Nation;

public class InputView {

    private static final String IS_CHO_TURN = "초나라 턴 입니다. 움직이고 싶은 말의 위치를 입력해주세요. (ex - 0,2)";
    private static final String IS_HAN_TURN = "한나라 턴 입니다. 움직이고 싶은 말의 위치를 입력해주세요. (ex - 0,2)";
    private static final String DESTINATION_PROMPT = "해당 말을 이동시킬 위치를 입려해 주세요.";

    private final Scanner scanner = new Scanner(System.in);

    public Position getPresentPosition(final Nation attatckNation) {
        if (attatckNation.equals(CHO)) {
            System.out.println(IS_CHO_TURN);
            return getUserPositionUntilValidate();
        }

        System.out.println(IS_HAN_TURN);
        return getUserPositionUntilValidate();
    }

    public Position getDestination() {
        System.out.println(DESTINATION_PROMPT);
        return getUserPositionUntilValidate();
    }

    private int parseInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 좌표는 숫자만 입력 할 수 있습니다.");
        }
    }

    private Position getUserPositionUntilValidate() {
        try {
            String inputPosition = readLine().trim();
            String[] split = inputPosition.split(",");
            return new Position(parseInt(split[0]), parseInt(split[1]));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getUserPositionUntilValidate();
        }
    }

    private String readLine() {
        return scanner.nextLine();
    }
}
