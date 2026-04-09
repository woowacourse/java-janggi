package view;

import exception.GameErrorMessage;
import exception.custom.InvalidGameInputException;
import java.util.Scanner;

public class InputView {
    private static final String HORSE_ELEPHANT_INPUT_GUIDE = "%s나라의 상마상마 상차림을 입력하세요: ";
    private static final String MOVED_PIECE_SOURCE_POSITION_INPUT_GUIDE = "이동시킬 말의 현재 위치를 입력하세요.(예: \"7,1\"): ";
    private static final String MOVED_PIECE_TARGET_POSITION_INPUT_GUIDE = "이동시킬 말의 도착 위치를 입력하세요.(예: \"6,1\"): ";

    private final Scanner scanner = new Scanner(System.in);

    public String readHorseElephantFormation(String teamName) {
        printMessage(String.format(HORSE_ELEPHANT_INPUT_GUIDE, teamName));
        String input = readInput();
        validateHorseElephantFormationInput(input);

        return input;
    }

    public String readSourcePosition() {
        printMessage(MOVED_PIECE_SOURCE_POSITION_INPUT_GUIDE);
        String input = readInput();
        validateSourcePosition(input);
        return input;
    }

    public String readTargetPosition() {
        printMessage(MOVED_PIECE_TARGET_POSITION_INPUT_GUIDE);
        String input = readInput();
        validateTargetPosition(input);
        return input;
    }

    /**
     * 핼퍼 메서드
     */
    private void printMessage(String message) {
        System.out.print(message);
    }

    private String readInput() {
        return scanner.nextLine();
    }

    /**
     * 검증 메서드
     */
    private void validateHorseElephantFormationInput(String input) {
        if (!input.equals("상마상마") && !input.equals("마상마상") && !input.equals("상마마상") && !input.equals("마상상마")) {
            throw new InvalidGameInputException(GameErrorMessage.INVALID_HORSE_ELEPHANT_FORMATION.getMessage());
        }
    }

    private void validateSourcePosition(String input) {
        validatePositionInputFormat(input);
    }

    private void validateTargetPosition(String input) {
        validatePositionInputFormat(input);
    }

    private void validatePositionInputFormat(String input) {
        try {
            String[] rowAndColumn = input.split(",");
            Integer.parseInt(String.valueOf(rowAndColumn[0]));
            Integer.parseInt(String.valueOf(rowAndColumn[1]));
        } catch (NumberFormatException e) {
            throw new InvalidGameInputException(GameErrorMessage.INVALID_POSITION_FORMAT.getMessage());
        }
    }
}
