package janggi.view;

import janggi.domain.board.Position;
import janggi.domain.board.initializer.ElephantSetUp;
import janggi.view.dto.CampDto;
import janggi.view.format.ElephantSetUpFormat;
import java.util.List;
import java.util.Scanner;

public final class InputView {

    private static final String INVALID_INPUT_FORMAT = "[ERROR] 잘못된 입력 형식입니다.";
    private static final String DELIMITER = ",";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String RESET = "\u001B[0m";

    private static final String ELEPHANT_SETTING = """
            
            %s나라의 상차림을 선택해주세요.
            %s""";

    private static final String TURN = LINE_SEPARATOR + "%s나라 차례 입니다.";
    private static final String SOURCE = "공격할 기물의 좌표를 행,열 순으로 입력해 주세요. (예: 9,8)";
    private static final String DESTINATION = LINE_SEPARATOR + "이동 시킬 목적지의 좌표를 행,열 순으로 입력해 주세요. (예: 2,0)";
    private static final String SELECT_GAME = "입장할 게임방 번호를 입력하세요. (새 게임 생성: 0)";

    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public ElephantSetUp readElephantSetting(CampDto campDto) {
        String campName = campDto.color() + campDto.name() + RESET;
        System.out.printf(
                (ELEPHANT_SETTING) + "%n",
                campName,
                ElephantSetUpFormat.outputMessage());
        return ElephantSetUpFormat.from(readLine()).toElephantSetting();
    }

    private String readLine() {
        String input = scanner.nextLine().strip();
        validateInput(input);
        return input;
    }

    private void validateInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT);
        }
    }

    public Position readSource(CampDto campDto) {
        String campName = campDto.color() + campDto.name() + RESET;
        System.out.printf((TURN) + "%n", campName);
        System.out.println(SOURCE);
        return toPosition(Parser.parseByDelimiter(DELIMITER, readLine()));
    }

    public Position readDestination() {
        System.out.println(DESTINATION);
        return toPosition(Parser.parseByDelimiter(DELIMITER, readLine()));
    }

    private Position toPosition(List<Integer> rawPosition) {
        validatePositionSize(rawPosition);
        return new Position(rawPosition.get(0), rawPosition.get(1));
    }

    private void validatePositionSize(List<Integer> rawPosition) {
        if (rawPosition.size() != 2) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT);
        }
    }

    public long readSelectedGameRoom() {
        System.out.println(SELECT_GAME);
        return Parser.parseToLong(readLine());
    }
}
