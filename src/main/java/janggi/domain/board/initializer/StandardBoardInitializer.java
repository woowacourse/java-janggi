package janggi.domain.board.initializer;

import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class StandardBoardInitializer implements BoardInitializer {

    private static final String INVALID_ELEPHANT_SETUP = "[ERROR] 진영별 상차림은 각각 하나씩만 존재해야 합니다.";

    private final Map<Camp, ElephantSetUp> elephantSetUps;

    public StandardBoardInitializer(Map<Camp, ElephantSetUp> elephantSetUps) {
        validate(elephantSetUps);
        this.elephantSetUps = Map.copyOf(elephantSetUps);
    }

    @Override
    public Map<Position, Piece> initialize() {
        Map<Position, Piece> board = new HashMap<>(InitialPiecePlacement.initialize());
        elephantSetUps.forEach((camp, elephantSetUp) -> board.putAll(elephantSetUp.settingUp(camp)));
        return board;
    }

    private void validate(Map<Camp, ElephantSetUp> elephantSetUps) {
        if (elephantSetUps.size() != Camp.getAllCamp().size()) {
            throw new IllegalArgumentException(INVALID_ELEPHANT_SETUP);
        }
    }
}
