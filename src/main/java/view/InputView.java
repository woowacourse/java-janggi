package view;

import domain.JanggiPosition;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public List<JanggiPosition> getMovePieceInput() {
        System.out.println("움직일 말의 위치와 원하는 도착지를 입력하세요. (ex. 01 45)");
        String position = scanner.nextLine();
        return List.of(
                parseToPosition(position.substring(0, 2)),
                parseToPosition(position.substring(3, 5))
        );
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
