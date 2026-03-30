package janggi.domain.board.initializer;

import janggi.domain.Position;
import janggi.domain.board.initializer.dto.ElephantSetUpDto;
import janggi.domain.piece.Piece;
import java.util.Map;

public class StandardBoardInitializer implements BoardInitializer {

    private static final String INVALID_ELEPHANT_SETUP = "[ERROR] 진영별 상차림은 각각 하나씩만 존재해야 합니다.";

    private final ElephantSetUpDto firstElephantSetUp;
    private final ElephantSetUpDto secondElephantSetUp;

    public StandardBoardInitializer(ElephantSetUpDto firstElephantSetUp, ElephantSetUpDto secondElephantSetUp) {
        validateDifferentCamp(firstElephantSetUp, secondElephantSetUp);
        this.firstElephantSetUp = firstElephantSetUp;
        this.secondElephantSetUp = secondElephantSetUp;
    }

    private void validateDifferentCamp(ElephantSetUpDto firstElephantSetUp, ElephantSetUpDto secondElephantSetUp) {
        if (firstElephantSetUp.camp() == secondElephantSetUp.camp()) {
            throw new IllegalArgumentException(INVALID_ELEPHANT_SETUP);
        }
    }

    @Override
    public Map<Position, Piece> initialize() {
        return InitialPiecePlacement.init(firstElephantSetUp, secondElephantSetUp);
    }
}
