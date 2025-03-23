package domain.strategy;

import java.util.Arrays;

public enum SettingUp {
    INNER_ELEPHANT(1, new InnerElephantStrategy()),
    OUTER_ELEPHANT(2, new OuterElephantStrategy()),
    RIGHT_ELEPHANT(3, new RightElephantStrategy()),
    LEFT_ELEPHANT(4, new LeftElephantStrategy());

    private final int command;
    private final SettingUpStrategy strategy;

    SettingUp(final int command, final SettingUpStrategy strategy) {
        this.command = command;
        this.strategy = strategy;
    }

    public static SettingUpStrategy findStrategyByCommand(final int command) {
        SettingUp setting = Arrays.stream(values())
                .filter(settingType -> settingType.command == command)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 올바른 커맨드 입력이 아닙니다."));

        return setting.strategy;
    }
}
