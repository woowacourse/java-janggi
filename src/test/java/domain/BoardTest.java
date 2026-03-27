package domain;

import domain.piece.Cannon;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Rook;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import strategy.InitializeStrategy;
import strategy.InnerElephantFormationStrategy;
import strategy.LeftElephantFormationStrategy;
import strategy.OuterElephantFormationStrategy;
import strategy.RightElephantFormationStrategy;

class BoardTest {
    private final InitializeStrategy noElephantHorseStrategy = new NoOpElephantHorseStrategy();

    static class NoOpElephantHorseStrategy extends InitializeStrategy {
        @Override
        protected Map<Position, Piece> initializeElephantHorseFormation(Team team) {
            return Collections.emptyMap();
        }
    }

    // TODO : 보드판 전체 초기화 확인

    /**
     * 1. 한나라 기본 기물이 올바르게 배치된다.(상,마 제외)
     */
    @Test
    void 한나라_기본_기물들이_올바르게_배치된다() {
        Board board = new Board(noElephantHorseStrategy, noElephantHorseStrategy);

        assertThat(board.isExistSameType(Position.from(1, 1), new Rook(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(1, 9), new Rook(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(1, 4), new Guard(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(1, 6), new Guard(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(1, 5), new King(Team.HAN))).isEqualTo(true);

        assertThat(board.isExistSameType(Position.from(3, 2), new Cannon(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(3, 8), new Cannon(Team.HAN))).isEqualTo(true);

        assertThat(board.isExistSameType(Position.from(4, 1), new Pawn(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(4, 3), new Pawn(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(4, 5), new Pawn(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(4, 7), new Pawn(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(4, 9), new Pawn(Team.HAN))).isEqualTo(true);
    }

    /**
     * 2. 초나라 기본 기물이 올바르게 배치된다. (상,마 제외)
     */
    @Test
    void 초나라_기본_기물들이_올바르게_배치된다() {
        Board board = new Board(noElephantHorseStrategy, noElephantHorseStrategy);

        assertThat(board.isExistSameType(Position.from(10, 1), new Rook(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(10, 4), new Guard(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(10, 5), new King(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(10, 6), new Guard(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(10, 9), new Rook(Team.CHO))).isEqualTo(true);

        assertThat(board.isExistSameType(Position.from(8, 2), new Cannon(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(8, 8), new Cannon(Team.CHO))).isEqualTo(true);

        assertThat(board.isExistSameType(Position.from(7, 1), new Pawn(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(7, 3), new Pawn(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(7, 5), new Pawn(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(7, 7), new Pawn(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(7, 9), new Pawn(Team.CHO))).isEqualTo(true);
    }

    /**
     * 상마상마 차림 검증 로직
     */
    /**
     * 1. 초나라가 상마상마를 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 초나라의_상마상마_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new LeftElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position choFirstElephant = Position.from(10, 2);
        Position choSecondElephant = Position.from(10, 7);
        Position choFirstHorse = Position.from(10, 3);
        Position choSecondHorse = Position.from(10, 8);

        assertThat(board.isExistSameType(choFirstElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choFirstHorse, new Horse(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondHorse, new Horse(Team.CHO))).isEqualTo(true);
    }

    /**
     * 2. 초나라가 상마마상을 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 초나라의_상마마상_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new OuterElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position choFirstElephant = Position.from(10, 2);
        Position choSecondElephant = Position.from(10, 8);
        Position choFirstHorse = Position.from(10, 3);
        Position choSecondHorse = Position.from(10, 7);

        assertThat(board.isExistSameType(choFirstElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choFirstHorse, new Horse(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondHorse, new Horse(Team.CHO))).isEqualTo(true);
    }

    /**
     * 3. 초나라가 마상마상을 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 초나라의_마상마상_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new RightElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position choFirstElephant = Position.from(10, 3);
        Position choSecondElephant = Position.from(10, 8);
        Position choFirstHorse = Position.from(10, 2);
        Position choSecondHorse = Position.from(10, 7);

        assertThat(board.isExistSameType(choFirstElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choFirstHorse, new Horse(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondHorse, new Horse(Team.CHO))).isEqualTo(true);
    }

    /**
     * 4. 초나라가 마상상마를 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 초나라의_마상상마_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new InnerElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position choFirstElephant = Position.from(10, 3);
        Position choSecondElephant = Position.from(10, 7);
        Position choFirstHorse = Position.from(10, 2);
        Position choSecondHorse = Position.from(10, 8);

        assertThat(board.isExistSameType(choFirstElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choFirstHorse, new Horse(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondHorse, new Horse(Team.CHO))).isEqualTo(true);
    }

    /**
     * 5. 한나라가 상마상마를 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 한나라의_상마상마_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new LeftElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position hanFirstElephant = Position.from(1, 3);
        Position hanSecondElephant = Position.from(1, 8);
        Position hanFirstHorse = Position.from(1, 2);
        Position hanSecondHorse = Position.from(1, 7);

        assertThat(board.isExistSameType(hanFirstElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanFirstHorse, new Horse(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondHorse, new Horse(Team.HAN))).isEqualTo(true);
    }

    /**
     * 6. 한나라가 상마마상을 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 한나라의_상마마상_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new OuterElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position hanFirstElephant = Position.from(1, 2);
        Position hanSecondElephant = Position.from(1, 8);
        Position hanFirstHorse = Position.from(1, 3);
        Position hanSecondHorse = Position.from(1, 7);

        assertThat(board.isExistSameType(hanFirstElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanFirstHorse, new Horse(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondHorse, new Horse(Team.HAN))).isEqualTo(true);
    }

    /**
     * 7. 한나라가 마상마상을 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 한나라의_마상마상_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new RightElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position hanFirstElephant = Position.from(1, 2);
        Position hanSecondElephant = Position.from(1, 7);
        Position hanFirstHorse = Position.from(1, 3);
        Position hanSecondHorse = Position.from(1, 8);

        assertThat(board.isExistSameType(hanFirstElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanFirstHorse, new Horse(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondHorse, new Horse(Team.HAN))).isEqualTo(true);
    }

    /**
     * 8. 한나라가 마상상마를 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 한나라의_마상상마_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new InnerElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position hanFirstElephant = Position.from(1, 3);
        Position hanSecondElephant = Position.from(1, 7);
        Position hanFirstHorse = Position.from(1, 2);
        Position hanSecondHorse = Position.from(1, 8);

        assertThat(board.isExistSameType(hanFirstElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanFirstHorse, new Horse(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondHorse, new Horse(Team.HAN))).isEqualTo(true);
    }

    /**
     * 보드 범위 테스트
     * 1. 기물의 목적지가 보드의 범위를 넘어가면 안된다.
     */

    @Test
    void 목적지가_보드의_범위를_넘어갈_경우_예외를_반환한다() {
        // given

        // when

        // then
    }
}
