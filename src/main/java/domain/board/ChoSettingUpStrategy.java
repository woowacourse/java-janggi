package domain.board;

import domain.Coordinate;
import domain.board.strategy.MaSangMaSang;
import domain.board.strategy.MaSangSangMa;
import domain.board.strategy.SangMaMaSang;
import domain.board.strategy.SangMaSangMa;
import domain.piece.Piece;
import java.util.Map;

public interface ChoSettingUpStrategy {

    Map<Coordinate, Piece> setUpCho();
    
    static ChoSettingUpStrategy selectStrategy(String settingUp) {
        return switch (settingUp) {
            case SangMaMaSang.SANG_MA_MA_SANG -> new SangMaMaSang();
            case MaSangSangMa.MA_SANG_SANG_MA -> new MaSangMaSang();
            case SangMaSangMa.SANG_MA_SANG_MA -> new SangMaSangMa();
            case MaSangMaSang.MA_SANG_MA_SANG -> new MaSangSangMa();
            default -> throw new IllegalArgumentException("[ERROR] 존재하지 않는 상차림 전략입니다.");
        };
    }

}
