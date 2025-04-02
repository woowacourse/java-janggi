package domain.board.setting;

import domain.piece.coordiante.Coordinate;
import domain.board.setting.strategy.MaSangMaSang;
import domain.board.setting.strategy.MaSangSangMa;
import domain.board.setting.strategy.SangMaMaSang;
import domain.board.setting.strategy.SangMaSangMa;
import domain.piece.Piece;
import java.util.Map;

public interface HanSettingUpStrategy {

    Map<Coordinate, Piece> setUpHan();

    static HanSettingUpStrategy selectStrategy(String settingUp) {
        return switch (settingUp) {
            case SangMaMaSang.SANG_MA_MA_SANG -> new SangMaMaSang();
            case MaSangSangMa.MA_SANG_SANG_MA -> new MaSangSangMa();
            case SangMaSangMa.SANG_MA_SANG_MA -> new SangMaSangMa();
            case MaSangMaSang.MA_SANG_MA_SANG -> new MaSangMaSang();
            default -> throw new IllegalArgumentException("[ERROR] 존재하지 않는 상차림 전략입니다.");
        };
    }

}
