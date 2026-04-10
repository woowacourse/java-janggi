package janggi.view;

import java.util.Arrays;

public enum UserCommand {
    YES("yes", true),
    NO("no", false),
    ;

    private final String command;
    private final boolean confirmed;

    UserCommand(String command, boolean confirmed) {
        this.command = command;
        this.confirmed = confirmed;
    }

    public static UserCommand from(String input) {
        return Arrays.stream(values())
                .filter(userCommand -> userCommand.command.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("유효한 커맨드(예는 yes, 아니오는 no)를 입력해 주세요."));
    }

    public boolean confirmed() {
        return confirmed;
    }
}
