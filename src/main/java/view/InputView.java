package view;

import exception.Validator;
import java.util.List;
import java.util.Scanner;
import util.InputParser;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int requestMaSangPosition() {
        System.out.println("[차 ( ) ( ) 사 ＋ 사 ( ) ( ) 차] ◀︎ 위치 선택");
        System.out.println("\n마,상의 위치를 번호로 입력해 주세요. (1~4 중에 입력)");
        System.out.println("""
                1. 마상상마
                2. 마상마상
                3. 상마상마
                4. 상마마상""");
        return Validator.validateNumber(scanner.nextLine());
    }

    public String requestPiece() {
        System.out.println("\n움작일 기물을 선택해주세요. (예: 졸, 차, 마, 등) : ");
        return scanner.nextLine();
    }

    public int requestStartPiecePosition() {
        System.out.println("\n움직일 기물의 좌표의 번호를 선택해주세요. ");
        return Validator.validateNumber(scanner.nextLine());
    }

    public List<Integer> requestMovePosition() {
        System.out.println("\n이동할 좌표를 입력해 주세요. ");
        return InputParser.splitBy(",", scanner.nextLine());
    }
}
