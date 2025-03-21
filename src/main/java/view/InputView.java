package view;

import domain.JanggiPosition;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public JanggiPosition getMovePieceInput() {
        System.out.println("움직일 말을 선택하세요. (ex. 01)");
        String position = scanner.nextLine();
        return parseToPosition(position);
    }

    public JanggiPosition getDestinationInput() {
        System.out.println("도착지를 선택하세요. (ex. 45)");
        String position = scanner.nextLine();
        return parseToPosition(position);
    }

    private JanggiPosition parseToPosition(String positionRaw) {
        try {
            int rank = Integer.parseInt(positionRaw.substring(0, 1));
            int file = Integer.parseInt(positionRaw.substring(1, 2));
            return new JanggiPosition(rank, file);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("위치는 숫자를 입력해야 합니다.");
        }
    }
}
