package boardSetting.strategyTest;

import domain.position.Position;
import domain.piece.Blank;
import domain.piece.Piece;
import domain.PieceProvider;
import domain.strategy.PawnStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnStrategyTest {

    private PawnStrategy pawnStrategy;
    private TestPieceProvider testBoard;

    @BeforeEach
    void setUp() {
        pawnStrategy = new PawnStrategy();
        testBoard = new TestPieceProvider();
    }

    @Test
    void 졸은_4가지_이동_후보_모두_반환() {
        Position currentPosition = new Position(5, 5);
        testBoard.setAllBlank();

        List<Position> candidates = pawnStrategy.getMoveCandidates(currentPosition, testBoard);

        assertThat(candidates).hasSize(4)
                .containsExactlyInAnyOrder(
                        new Position(4, 5), new Position(6, 5),
                        new Position(5, 4), new Position(5, 6)
                );
    }

    private static class TestPieceProvider implements PieceProvider {
        private final Map<Position, Boolean> boardState = new HashMap<>();
        private boolean defaultState = true;

        void setBlank(Position pos) {
            boardState.put(pos, false);
        }

        void setAllBlank() {
            this.defaultState = true;
        }

        @Override
        public boolean isBlank(Position position) {
            return boardState.getOrDefault(position, defaultState);
        }

        @Override
        public boolean isCannon(Position position) {
            return false;
        }

        @Override
        public Piece getPiece(Position position) {
            return new Blank();
        }
    }
}
