package view;

import exception.Validator;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int requestMaSangPosition() {
        System.out.println("마,상의 위치를 번호로 입력해 주세요. (1~4 중에 입력)");
        System.out.println("""
                1. 마상상마
                2. 마상마상
                3. 상마상마
                4. 상마마상""");
        return Validator.validateNumber(scanner.nextLine());
    }
}
