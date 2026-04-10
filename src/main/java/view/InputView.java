package view;


import java.util.Scanner;
import view.message.InputMessage;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public static GameStartOption readGameStartOption() {
        System.out.println(InputMessage.INPUT_GAME_START_OPTION.getMessage());
        String input = scanner.nextLine();
        return GameStartOption.from(input);
    }

    public static String selectPiecePosition() {
        System.out.println(InputMessage.SELECT_PIECE_POSITION_SIGN.getMessage());
        return scanner.nextLine();
    }

    public static String selectTargetPosition() {
        System.out.println(InputMessage.TARGET_POSITION_SIGN.getMessage());
        return scanner.nextLine();
    }
}
