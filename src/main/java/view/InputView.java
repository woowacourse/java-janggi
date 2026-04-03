package view;

import dto.InputPointDTO;
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

    private Scanner scanner = new Scanner(System.in);

    public int inputHanWingSetup() {
        System.out.print(HAN_WING_SETUP_MESSAGE);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int inputChoWingSetup() {
        System.out.print(CHO_WING_SETUP_MESSAGE);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public InputPointDTO inputMovePiecePoint() {
        System.out.print(INPUT_MOVE_PIECE_POINT_MESSAGE);
        return new InputPointDTO(scanner.nextLine());
    }

    public InputPointDTO inputDestinationPoint() {
        System.out.print(INPUT_DESTINATION_POINT_MESSAGE);
        return new InputPointDTO(scanner.nextLine());
    }
}
