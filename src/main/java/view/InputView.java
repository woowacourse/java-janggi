package view;

import exception.InvalidNumericInputException;
import java.util.Scanner;

public class InputView {
    private static final String INPUT_MOVE_PIECE_POINT_MESSAGE = "이동할 기물의 좌표를 입력하세요. (입력 형식: y좌표 x좌표)\n";
    private static final String INPUT_DESTINATION_POINT_MESSAGE = "기물의 목적지 좌표를 입력하세요. (입력 형식: y좌표 x좌표)\n";
    private static final String HAN_WING_SETUP_MESSAGE = "한(漢)의 상차림을 입력하세요."
            + "\n1. 마 - 상 - 마 - 상 (馬 - 象 - 馬 - 象)"
            + "\n2. 마 - 상 - 상 - 마 (馬 - 象 - 象 - 馬)"
            + "\n3. 상 - 마 - 상 - 마 (象 - 馬 - 象 - 馬)"
            + "\n4. 상 - 마 - 마 - 상 (象 - 馬 - 馬 - 象)\n";
    private static final String CHO_WING_SETUP_MESSAGE = "초(楚)의 상차림을 입력하세요."
            + "\n1. 마 - 상 - 마 - 상 (馬 - 象 - 馬 - 象)"
            + "\n2. 마 - 상 - 상 - 마 (馬 - 象 - 象 - 馬)"
            + "\n3. 상 - 마 - 상 - 마 (象 - 馬 - 象 - 馬)"
            + "\n4. 상 - 마 - 마 - 상 (象 - 馬 - 馬 - 象)\n";

    private final Scanner scanner = new Scanner(System.in);

    private int parseIntLine(String line) {
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            throw new InvalidNumericInputException();
        }
    }

    public int inputNewOrResume() {
        System.out.print("1. 새 게임\n2. 진행 중인 게임 재개\n번호를 입력하세요:");
        return parseIntLine(scanner.nextLine());
    }

    public int inputResumeGameChoice(int maxInclusive) {
        System.out.printf("재개할 게임 번호를 입력하세요 (1-%d):%n", maxInclusive);
        return parseIntLine(scanner.nextLine());
    }

    public int inputHanWingSetup() {
        System.out.print(HAN_WING_SETUP_MESSAGE);
        return parseIntLine(scanner.nextLine());
    }

    public int inputChoWingSetup() {
        System.out.print(CHO_WING_SETUP_MESSAGE);
        return parseIntLine(scanner.nextLine());
    }

    public String inputMovePiecePoint() {
        System.out.print(INPUT_MOVE_PIECE_POINT_MESSAGE);
        return scanner.nextLine();
    }

    public String inputDestinationPoint() {
        System.out.print(INPUT_DESTINATION_POINT_MESSAGE);
        return scanner.nextLine();
    }
}
