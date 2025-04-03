package view;

import dto.MoveCommandDTO;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final int SOURCE_POSITION_INDEX = 0;
    private static final int MOVE_PIECE_INDEX = 1;
    private static final int DESTINATION_POSITION_INDEX = 2;
    private static final String LOAD_GAME_CONFIRM = "y";


    private final Scanner scanner = new Scanner(System.in);

    public MoveCommandDTO readMoveCommand() {
        System.out.println("시작위치, 움직일기물, 목적위치를 입력해주세요(예시: 12 마 33)");

        String input = scanner.nextLine();
        if (!input.matches("\\d\\d\\s\\S\\s\\d\\d")) {
            throw new IllegalArgumentException("잘못된 형식의 입력입니다.");
        }

        List<String> commands = Arrays.stream(input.split(" "))
                .map(String::trim)
                .toList();

        return MoveCommandDTO.from(commands.get(SOURCE_POSITION_INDEX), commands.get(MOVE_PIECE_INDEX),
                commands.get(DESTINATION_POSITION_INDEX));
    }

    public boolean readLoadGameSelected() {
        System.out.println("기존의 게임을 이어서 하시겠습니다? (Y/N)");
        String input = scanner.nextLine().trim();

        return input.equalsIgnoreCase(LOAD_GAME_CONFIRM);
    }
}
