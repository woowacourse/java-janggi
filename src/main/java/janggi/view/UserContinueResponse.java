package janggi.view;

import java.util.Arrays;
import java.util.Objects;

public enum UserContinueResponse {
    CONTINUE("Y"),
    QUIT("N");

    private final String command;

    UserContinueResponse(String command) {
        this.command = command;
    }

    public static UserContinueResponse getValidUserResponse(String rawUserResponse) {
        return Arrays.stream(UserContinueResponse.values())
                .filter(userContinueResponse -> Objects.equals(userContinueResponse.getCommand(), rawUserResponse))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Y 혹은 N만 입력해 주세요."));
    }

    public String getCommand() {
        return command;
    }
}
