package janggi.view;

import janggi.position.Position;
import java.util.Scanner;

public class InputView {

    private static final int DX = 0;
    private static final int DY = 1;
    private static final int POSITION_SIZE = 2;
    private static final String POSITION_DELIMITER = ",";

    private final Scanner scanner = new Scanner(System.in);

    public Position readCurrentPosition(final String currentTurnTeam) {
        final String readPositionFormat = String.format(
                "%s 턴 입니다. 움직이고 싶은 말의 위치를 좌표로 입력해주세요.(ex 1,1). (좌표는 쉼표(,) 기준으로 구분)", currentTurnTeam);
        System.out.println(readPositionFormat);

        final String inputPosition = readLine().trim();

        final String[] splitPosition = inputSplit(inputPosition);
        validatePositionSize(splitPosition);

        return new Position(parseInt(splitPosition[DX]), parseInt(splitPosition[DY]));
    }

    public Position readTargetPosition() {
        System.out.println("해당 말을 이동시킬 위치를 좌표로 입력해 주세요.(좌표는 쉼표(,) 기준으로 구분)");

        final String inputPosition = readLine().trim();

        final String[] splitPosition = inputSplit(inputPosition);
        validatePositionSize(splitPosition);

        return new Position(parseInt(splitPosition[DX]), parseInt(splitPosition[DY]));
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
