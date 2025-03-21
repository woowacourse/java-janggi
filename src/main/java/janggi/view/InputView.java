package janggi.view;

import janggi.setting.CampType;
import janggi.setting.PieceAssignType;
import janggi.value.Position;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final static Scanner scanner = new Scanner(System.in);

    public PieceAssignType readPieceAssignType(final CampType campType) {
        System.out.printf("%s의 초기 배치를 선택해주세요.", campType.getName());
        System.out.println();
        System.out.println("1. 왼상(상마상마)");
        System.out.println("2. 오른상(마상마상)");
        System.out.println("3. 안상(마상상마)");
        System.out.println("4. 바깥상(상마마상)");

        PieceAssignType pieceAssignType = PieceAssignTypeAnswer.from(scanner.nextLine());
        System.out.println();
        return pieceAssignType;
    }

    public Position readMovedPiecePosition() {
        System.out.println("이동할 장기말의 좌표 입력해주세요.");
        return readPosition();
    }

    public Position readDestinationPosition() {
        System.out.println("목적지 좌표를 입력해주세요.");
        return readPosition();
    }

    private Position readPosition() {
        String line = scanner.nextLine();
        List<String> positionInput = List.of(line.split(","));
        try {
            int x = Integer.parseInt(positionInput.getFirst());
            int y = Integer.parseInt(positionInput.getLast());
            return new Position(x, y);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] 적절하지 않은 입력값입니다.");
        }
    }
}
