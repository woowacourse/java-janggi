package domain.board;

import domain.board.strategy.MaSangMaSang;
import domain.board.strategy.MaSangSangMa;
import domain.board.strategy.SangMaMaSang;
import domain.board.strategy.SangMaSangMa;

public enum SettingUp {

    SANG_MA_MA_SANG(new SangMaMaSang()),
    SANG_MA_SANG_MA(new SangMaSangMa()),
    MA_SANG_SANG_MA(new MaSangSangMa()),
    MA_SANG_MA_SANG(new MaSangMaSang());

    private final BoardSettingUpStrategy strategy;

    SettingUp(BoardSettingUpStrategy strategy) {
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

}
