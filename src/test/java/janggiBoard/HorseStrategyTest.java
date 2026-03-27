package janggiBoard;


import domain.Position;
import domain.piece.PieceProvider;
import domain.strategy.HorseStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class HorseStrategyTest {
    private HorseStrategy horseStrategy;
    private TestPieceProvider testBoard;

    @BeforeEach
    void setUp() {
        horseStrategy = new HorseStrategy();
        testBoard = new TestPieceProvider();
    }

    @Test
    void 먀_주변에_장애물_없으면_8가지_후보_모두_반환() {
        Position source = new Position(5, 5);
        // 모든 위치를 비어있는 상태(Blank)로 설정
        testBoard.setAllBlank(true);

        List<Position> candidates = horseStrategy.getMoveCandidates(source, testBoard);

        assertThat(candidates).hasSize(8)
                .containsExactlyInAnyOrder(
                        new Position(3, 4), new Position(3, 6), // 북쪽 기반
                        new Position(7, 4), new Position(7, 6), // 남쪽 기반
                        new Position(4, 3), new Position(6, 3), // 서쪽 기반
                        new Position(4, 7), new Position(6, 7)  // 동쪽 기반
                );
    }

    @Test
    void 북쪽_멱이_막혀있으면_복서_북동으로_이동할수_없다(){
        Position source = new Position(5, 5);
        testBoard.setAllBlank(true);

        // 북쪽 멱 위치를 막힌 상태로 설정
        testBoard.setBlank(new Position(4, 5), false);

        List<Position> candidates = horseStrategy.getMoveCandidates(source, testBoard);

        // 전체 8개 중 북쪽 기반 2개가 제외된 6개만 존재해야 함
        assertThat(candidates).hasSize(6)
                .doesNotContain(new Position(3, 4), new Position(3, 6));
    }

    // 테스트를 위한 간단한 PieceProvider 구현체
    private static class TestPieceProvider implements PieceProvider {
        private final Map<Position, Boolean> boardState = new HashMap<>();
        private boolean defaultState = true;

        void setBlank(Position pos, boolean isBlank) {
            boardState.put(pos, isBlank);
        }

        void setAllBlank(boolean isBlank) {
            this.defaultState = isBlank;
        }

        @Override
        public boolean isBlank(Position position) {
            return boardState.getOrDefault(position, defaultState);
        }
    }
}
