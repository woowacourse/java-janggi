package janggiBoard.strategyTest;

import domain.position.Position;
import domain.piece.Blank;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import domain.strategy.PalaceStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class PalaceStrategyTest {

    private PalaceStrategy palaceStrategy;
    private TestPieceProvider testBoard;

    @BeforeEach
    void setUp() {
        palaceStrategy = new PalaceStrategy();
        testBoard = new TestPieceProvider();
    }

    @Test
    void 궁과_사가_주변에_장애물이_없다면_8가지_후보_모두_반환() {
        Position currentPosition = new Position(5, 5);
        testBoard.setAllBlank();

        List<Position> candidates = palaceStrategy.getMoveCandidates(currentPosition, testBoard);

        assertThat(candidates).hasSize(8)
                .containsExactlyInAnyOrder(
                        new Position(4, 5), new Position(4, 4),
                        new Position(4, 6), new Position(5, 4),
                        new Position(5, 6), new Position(6, 4),
                        new Position(6, 5), new Position(6, 6)
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
        public Piece getPiece(Position position) {
            return new Blank();
        }
    }
}
