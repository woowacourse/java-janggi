package janggiBoard.strategyTest;


import domain.position.Position;
import domain.piece.Blank;
import domain.piece.Piece;
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
    void 마_주변에_장애물_없으면_8가지_후보_모두_반환() {
        Position position = new Position(5, 5);
        testBoard.setAllBlank();

        List<Position> candidates = horseStrategy.getMoveCandidates(position, testBoard);

        assertThat(candidates).hasSize(8)
                .containsExactlyInAnyOrder(
                        new Position(3, 4), new Position(3, 6), // 북쪽 기반
                        new Position(7, 4), new Position(7, 6), // 남쪽 기반
                        new Position(4, 3), new Position(6, 3), // 서쪽 기반
                        new Position(4, 7), new Position(6, 7)  // 동쪽 기반
                );
    }

    @Test
    void 북쪽_멱이_막혀있으면_북서_북동으로_이동할수_없다(){
        Position source = new Position(5, 5);
        testBoard.setAllBlank();

        // 북쪽 멱 위치를 막힌 상태로 설정
        testBoard.setBlank(new Position(4, 5));
        List<Position> candidates = horseStrategy.getMoveCandidates(source, testBoard);

        assertThat(candidates).hasSize(6)
                .doesNotContain(new Position(3, 4), new Position(3, 6));
    }

    @Test
    void 남쪽_멱이_막혀있으면_남서_남동으로_이동할수_없다() {
        Position position = new Position(5, 5);
        testBoard.setAllBlank();

        testBoard.setBlank(new Position(6, 5));
        List<Position> candidates = horseStrategy.getMoveCandidates(position, testBoard);

        assertThat(candidates).hasSize(6)
                .doesNotContain(new Position(7,4), new Position(7,6));
    }

    @Test
    void 서쪽_멱이_막혀있으면_북서_남서로_이동할수_없다() {
        Position position = new Position(5, 5);
        testBoard.setAllBlank();

        testBoard.setBlank(new Position(5, 4));
        List<Position> candidates = horseStrategy.getMoveCandidates(position, testBoard);

        assertThat(candidates).hasSize(6)
                .doesNotContain(new Position(6,5), new Position(4,3));
    }

    @Test
    void 동쪽_멱이_막혀있으면_북동_남동으로_이동할수_없다() {
        Position position = new Position(5, 5);
        testBoard.setAllBlank();

        testBoard.setBlank(new Position(5, 6));
        List<Position> candidates = horseStrategy.getMoveCandidates(position, testBoard);

        assertThat(candidates).hasSize(6)
                .doesNotContain(new Position(4, 7), new Position(6, 7));
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
