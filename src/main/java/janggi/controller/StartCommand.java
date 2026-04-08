package janggi.controller;

public enum StartCommand {
    CREATE_NEW_GAME(1),
    LOAD_PREVIOUS_GAME(2),
    QUIT(3);

    private static final int MIN_SETUP_COMMAND = 1;
    private static final int MAX_SETUP_COMMAND = 3;
    private final int commandNumber;

    StartCommand(final int commandNumber) {
        this.commandNumber = commandNumber;
    }

    public static StartCommand from(final int inputCommand) {
        for (StartCommand command : StartCommand.values()) {
            if (command.commandNumber == inputCommand) {
                return command;
            }
        }
        throw new IllegalArgumentException("입력은 " + MIN_SETUP_COMMAND + "에서 " + MAX_SETUP_COMMAND + "까지의 정수 값이어야 합니다.");
    }
}
