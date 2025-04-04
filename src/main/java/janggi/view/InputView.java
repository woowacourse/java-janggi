package janggi.view;

import janggi.domain.setting.AssignType;
import janggi.domain.setting.CampType;
import janggi.domain.value.JanggiPosition;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final static String COMMA = ",";
    private final static int POSITION_COUNTS = 2;
    private final static Scanner scanner = new Scanner(System.in);

    public AssignType readAnswer(final CampType campType) {
        System.out.printf("%s의 초기 배치를 선택해주세요.", campType.getName());
        System.out.println();
        System.out.println("1. 왼상(상마상마)");
        System.out.println("2. 오른상(마상마상)");
        System.out.println("3. 안상(마상상마)");
        System.out.println("4. 바깥상(상마마상)");

        AssignType assignType = Answer.from(scanner.nextLine());

        System.out.println();
        return assignType;
    }

    public JanggiPosition readMovedPiecePosition() {
        System.out.println("이동할 장기말의 좌표 입력해주세요.");
        return getJanggiPosition();
    }

    public JanggiPosition readDestinationPosition() {
        System.out.println("목적지 좌표를 입력해주세요.");
        return getJanggiPosition();
    }

    private JanggiPosition getJanggiPosition() {
        String line = scanner.nextLine();
        List<String> positionInput = List.of(line.split(COMMA));
        validatePositionCounts(positionInput);
        int x = validateNumber(positionInput.getFirst());
        int y = validateNumber(positionInput.getLast());

        System.out.println();
        return new JanggiPosition(x, y);
    }

    private int validateNumber(String position) {
        try {
            return Integer.parseInt(position);
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException("[ERROR] 좌표는 숫자로 입력하셔야 합니다.");
        }
    }

    private void validatePositionCounts(List<String> positionInput) {
        if (positionInput.size() != POSITION_COUNTS) {
            throw new IllegalArgumentException("[ERROR] (x,y)를 입력하셔야 합니다.");
        }
    }
}
