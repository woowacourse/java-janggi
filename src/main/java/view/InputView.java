package view;

import controller.dto.MovedPieceRequest;
import java.util.Scanner;

public class InputView {
    private static final String CHO_HORSE_ELEPHANT_INPUT_GUIDE = "초나라의 상마상마 상차림을 입력하세요: ";
    private static final String HAN_HORSE_ELEPHANT_INPUT_GUIDE = "한나라의 상마상마 상차림을 입력하세요: ";
    private static final String MOVED_PIECE_INPUT_GUIDE= "이동시킬 말의 이름과 현재 위치(행,열), 옮기고 싶은 위치(행,열)를 함께 입력하세요.(예: \"(7,1), (6,1), 졸\"): ";

    private final Scanner scanner = new Scanner(System.in);

    public String readChoHorseElephantFormation(){
        printMessage(CHO_HORSE_ELEPHANT_INPUT_GUIDE);
        return readInput();
    }

    public String readHanHorseElephantFormation(){
        printMessage(HAN_HORSE_ELEPHANT_INPUT_GUIDE);
        return readInput();
    }

    public MovedPieceRequest readMovedPieceInput(){
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

    private MovedPieceRequest parseMovedPieceInput(String input){
        String[] splitedInput = input.split(" ");

        int currentRow = Integer.parseInt(String.valueOf(splitedInput[0].charAt(1)));
        int currentColumn = Integer.parseInt(String.valueOf(splitedInput[0].charAt(3)));
        int nextRow = Integer.parseInt(String.valueOf(splitedInput[1].charAt(1)));
        int nextColumn = Integer.parseInt(String.valueOf(splitedInput[1].charAt(3)));
        String pieceType = splitedInput[2];

        return new MovedPieceRequest(currentRow, currentColumn, nextRow, nextColumn, pieceType);
    }
}
