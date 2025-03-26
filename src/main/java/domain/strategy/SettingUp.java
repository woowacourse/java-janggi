package domain.strategy;

import java.util.Arrays;

public enum SettingUp {
    INNER_ELEPHANT(1, new InnerElephantInitializer()),
    OUTER_ELEPHANT(2, new OuterElephantInitializer()),
    RIGHT_ELEPHANT(3, new RightElephantInitializer()),
    LEFT_ELEPHANT(4, new LeftElephantInitializer());

    private final int command;
    private final SettingUpInitializer strategy;

    SettingUp(final int command, final SettingUpInitializer strategy) {
        this.command = command;
        this.strategy = strategy;
    }

    public static SettingUpInitializer findStrategyByCommand(final int command) {
        SettingUp setting = Arrays.stream(values())
                .filter(settingType -> settingType.command == command)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 커맨드 입력이 아닙니다."));

        return setting.strategy;
    }
}
