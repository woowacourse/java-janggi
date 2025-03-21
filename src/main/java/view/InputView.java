package view;

import java.util.Scanner;
import position.BoardPosition;

public class InputView {

    private static final int DX = 0;
    private static final int DY = 1;
    private static final int POSITION_SIZE = 2;
    private static final String POSITION_DELIMITER = ",";

    private final Scanner scanner = new Scanner(System.in);

    public BoardPosition readPresentPosition() {
        System.out.println("초나라 턴 입니다. 움직이고 싶은 말의 위치를 입력해주세요.(좌표는 쉼표(,) 기준으로 구분)");

        String inputPosition = readLine().trim();

        String[] splitPosition = inputSplit(inputPosition);
        validatePositionSize(splitPosition);

        return new BoardPosition(parseInt(splitPosition[DX]), parseInt(splitPosition[DY]));
    }

    public BoardPosition readFuturePosition() {
        System.out.println("해당 말을 이동시킬 위치를 입려해 주세요.(좌표는 쉼표(,) 기준으로 구분)");

        String inputPosition = readLine().trim();

        String[] splitPosition = inputSplit(inputPosition);
        validatePositionSize(splitPosition);

        return new BoardPosition(parseInt(splitPosition[DX]), parseInt(splitPosition[DY]));
    }

    private void validatePositionSize(final String[] split) {
        if (split.length != POSITION_SIZE) {
            throw new IllegalArgumentException("[ERROR] 좌표는 쉼표로 구분된 두개를 입력해야 합니다.");
        }
    }

    private String[] inputSplit(final String inputPosition) {
        if (!inputPosition.contains(POSITION_DELIMITER)) {
            throw new IllegalArgumentException("[ERROR] 좌표는 쉼표(,)로 구분해야 합니다.");
        }
        return inputPosition.split(POSITION_DELIMITER);
    }

    private int parseInt(final String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 좌표는 숫자만 입력 할 수 있습니다.");
        }
    }

    private String readLine() {
        return scanner.nextLine();
    }
}
