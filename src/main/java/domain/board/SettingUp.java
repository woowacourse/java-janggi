package domain.board;

import domain.board.strategy.MaSangMaSang;
import domain.board.strategy.MaSangSangMa;
import domain.board.strategy.SangMaMaSang;
import domain.board.strategy.SangMaSangMa;

public enum SettingUp {

    SANG_MA_MA_SANG("상마마상", new SangMaMaSang()),
    SANG_MA_SANG_MA("상마상마", new SangMaSangMa()),
    MA_SANG_SANG_MA("마상상마", new MaSangSangMa()),
    MA_SANG_MA_SANG("마상마상", new MaSangMaSang());

    private final String settingUpName;
    private final BoardSettingUpStrategy strategy;

    SettingUp(String settingUpName, BoardSettingUpStrategy strategy) {
        this.settingUpName = settingUpName;
        this.strategy = strategy;
    }

    public static SettingUp of(String settingUp) {
        return switch (settingUp) {
            case "상마마상" -> SANG_MA_MA_SANG;
            case "상마상마" -> SANG_MA_SANG_MA;
            case "마상상마" -> MA_SANG_SANG_MA;
            case "마상마상" -> MA_SANG_MA_SANG;
            default -> throw new IllegalArgumentException("[ERROR] 존재하지 않는 상차림 전략입니다.");
        };
    }

    public BoardSettingUpStrategy getStrategy() {
        return strategy;
    }

    public String getSettingUpName() {
        return settingUpName;
    }
}
