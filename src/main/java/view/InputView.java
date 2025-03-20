package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public List<String> readPlayerNames() {
        System.out.println("플레이어 두 명의 이름을 입력하시오. (플레이어1, 플레이어2)");
        String input = scanner.nextLine();
        return Arrays.asList(input.split(","));
    }

    public List<Integer> readMovePiecePosition() {
        System.out.println("움직일 말의 좌표를 입력하세요");
        String input = scanner.nextLine();
        //11, 11
        String[] positions = input.split(",");
        int row = Integer.parseInt(positions[0]);
        int column = Integer.parseInt(positions[1]);
        return List.of(row, column);

    }

    public List<Integer> readTargetPosition() {
        System.out.println("이동할 좌표를 입력하세요.");
        String input = scanner.nextLine();
        //11, 11
        String[] positions = input.split(",");
        int row = Integer.parseInt(positions[0]);
        int column = Integer.parseInt(positions[1]);
        return List.of(row, column);
    }
}
