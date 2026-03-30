package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import java.util.Map;

public class StandardBoardInitializer implements BoardInitializer {

    private final ElephantSetUp hanElephantSetUp;
    private final ElephantSetUp choElephantSetUp;

    // TODO : 둘 다 같은 나라의 ElephantSetting 들어와도 컴파일 에러 X -> 타입 강제 고려하기
    public StandardBoardInitializer(ElephantSetUp hanElephantSetUp, ElephantSetUp choElephantSetUp) {
        this.hanElephantSetUp = hanElephantSetUp;
        this.choElephantSetUp = choElephantSetUp;
    }

    @Override
    public Map<Position, Piece> initialize() {
        return InitialPiecePlacement.init(hanElephantSetUp, choElephantSetUp);
    }
}
