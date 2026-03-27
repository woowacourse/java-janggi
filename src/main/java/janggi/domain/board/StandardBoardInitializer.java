package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import java.util.Map;

public class StandardBoardInitializer implements BoardInitializer {

    private final ElephantSetting hanElephantSetting;
    private final ElephantSetting choElephantSetting;

    // TODO : 둘 다 같은 나라의 ElephantSetting 들어와도 컴파일 에러 X -> 타입 강제 고려하기
    public StandardBoardInitializer(ElephantSetting hanElephantSetting, ElephantSetting choElephantSetting) {
        this.hanElephantSetting = hanElephantSetting;
        this.choElephantSetting = choElephantSetting;
    }

    @Override
    public Map<Position, Piece> initialize() {
        return InitialPiecePlacement.init(hanElephantSetting, choElephantSetting);
    }
}
