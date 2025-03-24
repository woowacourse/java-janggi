package janggi.view;

import janggi.exception.ErrorException;
import janggi.piece.Camp;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String MOVEMENT_DELIMITER = ",";
    private static final int MOVEMENT_LENGTH = 2;

    private final Scanner scanner = new Scanner(System.in);

    public Command askStartCommand() {
        String response = prompt(formatCommands());
        Command command = Command.findCommandByCode(response);
        validateStartCommand(command);
        return command;
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

    public List<String> readMovement(Camp camp) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append(String.format("[%s의 차례입니다.]", camp.getName()))
                .append(System.lineSeparator())
                .append("이동시킬 기물의 좌표와 도착 지점의 좌표를 입력해 주세요. 예) 03,13");
        String response = prompt(stringBuilder.toString());
        return parseMovement(response);
    }

    private void validateStartCommand(Command command) {
        if (command != Command.START) {
            throw new ErrorException("게임을 시작하려면 START를 입력해야 합니다.");
        }
    }

    private void validatePlayCommand(Command command) {
        if (command == Command.START) {
            throw new ErrorException("시작한 게임을 START 외 다른 명령어를 입력해야 합니다.");
        }
    }

    private List<String> parseMovement(String response) {
        String[] split = response.split(MOVEMENT_DELIMITER, -1);
        if (split.length != MOVEMENT_LENGTH) {
            throw new ErrorException("출발 좌표와 도착 좌표, 2개의 좌표를 입력해야 합니다.");
        }
        return Arrays.stream(split)
                .map(String::trim)
                .toList();
    }

    private String prompt(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
