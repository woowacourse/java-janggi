package janggiBoard.strategyTest;

import domain.Position;
import domain.Team;
import domain.piece.*;
import domain.strategy.ChariotStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ChariotStrategyTest {

    private ChariotStrategy chariotStrategy;
    private TestPieceProvider testBoard;

    @BeforeEach
    void setUp() {
        chariotStrategy = new ChariotStrategy();
        testBoard = new TestPieceProvider();
    }

    @Test
    void 차는_상하좌우_직선_모든칸_후보로_반환한다() {
        Position position = new Position(5, 4);
        testBoard.setAllBlank();

        List<Position> candidates = chariotStrategy.getMoveCandidates(position, testBoard);

        assertThat(candidates).hasSize(17);
        assertThat(candidates).contains(new Position(0, 4),
                new Position(9, 4),
                new Position(5, 0),
                new Position(5, 8));
    }

    @Test
    void 이동경로에_장애물_있으면_그_지점까지_이동후_전진하지_않는다() {
        Position position = new Position(5, 4);
        testBoard.setAllBlank();

        Position obstacle = new Position(3, 4);
        testBoard.setBlank(obstacle);

        List<Position> candidates = chariotStrategy.getMoveCandidates(position, testBoard);

        assertThat(candidates).contains(new Position(4, 4), new Position(3, 4));
        assertThat(candidates).doesNotContain(new Position(2, 4),
                new Position(1, 4),
                new Position(0, 4));
    }

    private static class TestPieceProvider implements PieceProvider {
        private final Map<Position, Boolean> boardState = new HashMap<>();
        private boolean defaultState = true;

        void setBlank(Position position) {
            boardState.put(position, false);
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
            if (isBlank(position)) {
                return new Pawn(Team.HAN);
            }
            return new Chariot(Team.CHO);
        }
    }
}
