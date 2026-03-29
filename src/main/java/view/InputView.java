package view;

import java.util.Scanner;

public class InputView {
    private static final String CHO_HORSE_ELEPHANT_INPUT_GUIDE = "초나라의 상마상마 상차림을 입력하세요: ";
    private static final String HAN_HORSE_ELEPHANT_INPUT_GUIDE = "한나라의 상마상마 상차림을 입력하세요: ";

    private final Scanner scanner = new Scanner(System.in);

    public String readChoHorseElephantFormation(){
        printMessage(CHO_HORSE_ELEPHANT_INPUT_GUIDE);
        return readInput();
    }

    public String readHanHorseElephantFormation(){
        printMessage(HAN_HORSE_ELEPHANT_INPUT_GUIDE);
        return readInput();
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
}
