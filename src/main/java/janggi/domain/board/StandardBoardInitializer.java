package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import java.util.Map;

public class StandardBoardInitializer implements BoardInitializer {

    private final String hanChoice;
    private final String choChoice;

    // TODO : 둘 다 같은 나라의 ElephantSetting 들어와도 컴파일 에러 X -> 타입 강제 고려하기
    // TODO : String으로 바꿔도 같은 문제 발생
    public StandardBoardInitializer(String hanChoice, String choChoice) {
        this.hanChoice = hanChoice;
        this.choChoice = choChoice;
    }

    @Override
    public Map<Position, Piece> initialize() {
        ElephantSetting hanElephantSetting = ElephantSetting.findElephantSettingBy(hanChoice);
        ElephantSetting choElephantSetting = ElephantSetting.findElephantSettingBy(choChoice);

        return InitialPiecePlacement.init(hanElephantSetting, choElephantSetting);
    }
}
