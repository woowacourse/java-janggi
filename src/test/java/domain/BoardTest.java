package domain;

import domain.piece.Elephant;
import domain.piece.Horse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import strategy.InitializeStrategy;
import strategy.InnerElephantFormationStrategy;
import strategy.LeftElephantFormationStrategy;
import strategy.OuterElephantFormationStrategy;
import strategy.RightElephantFormationStrategy;

class BoardTest {
    /**
     * 보드판 전체 초기화 테스트 로직
     */
    /**
     * 1. 한나라 전체 기물이 올바르게 배치된다.
     */
    /**
     * 2. 초나라 전체 기물이 올바르게 배치된다.
     */

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
}
