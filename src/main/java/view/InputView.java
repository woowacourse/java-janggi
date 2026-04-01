package view;

import exception.GameErrorMessage;
import exception.custom.InvalidGameInputException;
import java.util.Scanner;

public class InputView {
    private static final String HORSE_ELEPHANT_INPUT_GUIDE = "%s나라의 상마상마 상차림을 입력하세요: ";
    private static final String MOVED_PIECE_SOURCE_POSITION_INPUT_GUIDE = "이동시킬 말의 이름과 현재 위치(행,열)를 함께 입력하세요.(예: \"(7,1), 졸\"): ";
    private static final String MOVED_PIECE_TARGET_POSITION_INPUT_GUIDE = "이동시킬 말의 도착 위치(행,열)를 함께 입력하세요.(예: \"(6,1)\"): ";

    private final Scanner scanner = new Scanner(System.in);

    public String readHorseElephantFormation(String teamName) {
        printMessage(String.format(HORSE_ELEPHANT_INPUT_GUIDE, teamName));
        String input = readInput();
        validateHorseElephantFormationInput(input);

        return input;
    }

    public String readSourcePositionAndPieceType() {
        printMessage(MOVED_PIECE_SOURCE_POSITION_INPUT_GUIDE);
        String input = readInput();
        validateSourcePositionAndPieceType(input);
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

    private void validateSourcePositionAndPieceType(String input) {
        String[] sourceAndPiece = input.split(" ");
        validatePositionInputFormat(sourceAndPiece[0]);
        validatePieceTypeInputFormat(sourceAndPiece[1]);
    }

    private void validateTargetPosition(String input) {
        validatePositionInputFormat(input);
    }

    private void validatePositionInputFormat(String input) {
        try {
            Integer.parseInt(String.valueOf(input.charAt(1)));
            Integer.parseInt(String.valueOf(input.charAt(3)));
        } catch (NumberFormatException e) {
            throw new InvalidGameInputException(GameErrorMessage.INVALID_POSITION_FORMAT.getMessage());
        }
    }

    private void validatePieceTypeInputFormat(String input) {
        if (input.length() != 1) {
            throw new InvalidGameInputException(GameErrorMessage.INVALID_PIECE_TYPE_FORMAT.getMessage());
        }
    }
}
