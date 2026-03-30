package view;

import controller.dto.MovedPieceRequest;
import exception.GameErrorMessage;
import exception.custom.InvalidGameInputException;
import java.util.Scanner;

public class InputView {
    private static final String HORSE_ELEPHANT_INPUT_GUIDE = "%s나라의 상마상마 상차림을 입력하세요: ";
    private static final String MOVED_PIECE_INPUT_GUIDE = "이동시킬 말의 이름과 현재 위치(행,열), 옮기고 싶은 위치(행,열)를 함께 입력하세요.(예: \"(7,1), (6,1), 졸\"): ";

    private final Scanner scanner = new Scanner(System.in);

    public String readHorseElephantFormation(String teamName) {
        printMessage(String.format(HORSE_ELEPHANT_INPUT_GUIDE, teamName));
        String input = readInput();
        validateHorseElephantFormationInput(input);

        return input;
    }

    public MovedPieceRequest readMovedPieceInput() {
        printMessage(MOVED_PIECE_INPUT_GUIDE);
        return parseMovedPieceInput(readInput());
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

    private MovedPieceRequest parseMovedPieceInput(String input) {
        String[] splitedInput = input.split(" ");

        int currentRow = Integer.parseInt(String.valueOf(splitedInput[0].charAt(1)));
        int currentColumn = Integer.parseInt(String.valueOf(splitedInput[0].charAt(3)));
        int nextRow = Integer.parseInt(String.valueOf(splitedInput[1].charAt(1)));
        int nextColumn = Integer.parseInt(String.valueOf(splitedInput[1].charAt(3)));
        String pieceType = splitedInput[2];

        return new MovedPieceRequest(currentRow, currentColumn, nextRow, nextColumn, pieceType);
    }

    /**
     * 검증 메서드
     */
    private void validateHorseElephantFormationInput(String input) {
        if (!input.equals("상마상마") && !input.equals("마상마상") && !input.equals("상마마상") && !input.equals("마상상마")) {
            throw new InvalidGameInputException(GameErrorMessage.INVALID_HORSE_ELEPHANT_FORMATION.getMessage());
        }
    }
}
