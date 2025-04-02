package janggi.view;

import janggi.exception.ErrorException;
import janggi.domain.piece.Camp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String MOVEMENT_DELIMITER = ",";
    private static final int MOVEMENT_LENGTH = 2;

    private final Scanner scanner = new Scanner(System.in);

    public Command askStartOrRecordCommand() {
        String response = prompt(formatCommands());
        Command command = Command.findCommandByCode(response);
        validateStartOrRecordCommand(command);
        return command;
    }

    public String askBoardId() {
        return prompt("게임 진행을 할 보드 ID를 입력하시오.");
    }

    public Command askPlayCommand() {
        String response = prompt(formatCommands());
        Command command = Command.findCommandByCode(response);
        validatePlayCommand(command);
        return command;
    }

    private String formatCommands() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append("장기 게임 명령어를 입력하시오. 예) START");
        for (Command command : Command.values()) {
            stringBuilder.append(System.lineSeparator()).append(command.toString());
        }
        return stringBuilder.toString();
    }

    public List<List<Integer>> readMovement(Camp camp) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append(String.format("[%s의 차례입니다.]", camp.getName()))
                .append(System.lineSeparator())
                .append("이동시킬 기물의 좌표와 도착 지점의 좌표를 입력해 주세요. 예) 03,13");
        String response = prompt(stringBuilder.toString());
        return parseMovement(response);
    }

    private void validateStartOrRecordCommand(Command command) {
        if (command != Command.START && command != Command.RESUME) {
            throw new ErrorException("게임을 시작하지 않은 상태에서 START 또는 RESUME 명령어만 입력해야 합니다.");
        }
    }

    private void validatePlayCommand(Command command) {
        if (command == Command.START || command == Command.RESUME) {
            throw new ErrorException("시작한 게임을 START와 RESUME 외 다른 명령어를 입력해야 합니다.");
        }
    }

    private List<List<Integer>> parseMovement(String response) {
        List<String> positions = getPositionsOf(response);
        List<List<Integer>> movement = new ArrayList<>();
        for (String xy : positions) {
            movement.add(parsePositionOf(xy));
        }
        return movement;
    }

    private List<String> getPositionsOf(String response) {
        String[] split = response.split(MOVEMENT_DELIMITER, -1);
        if (split.length != MOVEMENT_LENGTH) {
            throw new ErrorException("출발 좌표와 도착 좌표, 2개의 좌표를 입력해야 합니다.");
        }
        return Arrays.stream(split).map(String::trim).toList();
    }

    private List<Integer> parsePositionOf(String xy) {
        List<Integer> position = new ArrayList<>();
        if (xy.length() != 2) {
            throw new ErrorException("좌표는 x, y만 입력 가능합니다.");
        }
        try {
            String[] split = xy.split("", -1);
            position.add(Integer.parseInt(split[0]));
            position.add(Integer.parseInt(split[1]));
        } catch (NumberFormatException e) {
            throw new ErrorException("좌표는 숫자만 입력 가능합니다.");
        }
        return position;
    }

    private String prompt(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
