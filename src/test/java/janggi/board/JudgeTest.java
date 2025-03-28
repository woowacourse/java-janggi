package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.point.Point;
import janggi.piece.Camp;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JudgeTest {

    @DisplayName("장군이 모두 살아있는 경우 게임이 종료되지 않는다.")
    @Test
    void isGameOverTest_WhenAllGeneralsAreAlive() {
        // given
        Board board = new Board();
        board.placePiece(new Point(4, 1), new General(Camp.CHU));
        board.placePiece(new Point(4, 8), new General(Camp.HAN));
        Judge judge = new Judge();

        // when
        boolean result = judge.isGameOver(board);

        // then
        assertThat(result)
                .isFalse();
    }

    @DisplayName("장군이 하나만 살아있는 경우 게임이 종료된다.")
    @Test
    void isGameOverTest_WhenOneGeneralIsDead() {
        // given
        Board board = new Board();
        board.placePiece(new Point(4, 1), new General(Camp.CHU));
        Judge judge = new Judge();

        // when
        boolean result = judge.isGameOver(board);

        // then
        assertThat(result)
                .isTrue();
    }

    @DisplayName("초기 보드판에서 게임이 종료되지 않는다.")
    @Test
    void isGameOverTest_WhenInitialBoard() {
        // given
        Board board = BoardGenerator.generate();
        Judge judge = new Judge();

        // when
        boolean result = judge.isGameOver(board);

        // then
        assertThat(result)
                .isFalse();
    }

    @DisplayName("진영별 점수를 계산한다.")
    @Test
    void calculateScoreTest() {
        // given
        Board board = new Board();
        board.placePiece(new Point(0, 0), new Chariot(Camp.CHU));
        board.placePiece(new Point(1, 0), new Elephant(Camp.CHU));
        board.placePiece(new Point(2, 0), new Horse(Camp.CHU));
        board.placePiece(new Point(3, 0), new Guard(Camp.CHU));
        board.placePiece(new Point(5, 9), new Guard(Camp.HAN));
        board.placePiece(new Point(6, 9), new Elephant(Camp.HAN));
        board.placePiece(new Point(7, 9), new Horse(Camp.HAN));
        board.placePiece(new Point(8, 9), new Chariot(Camp.HAN));
        Judge judge = new Judge();

        // when
        Map<Camp, Double> result = judge.calculateScore(board);

        // then
        assertThat(result)
                .containsEntry(Camp.CHU, 24.0)
                .containsEntry(Camp.HAN, 25.5);
    }

    @DisplayName("초기 보드판의 점수를 계산한다.")
    @Test
    void calculateScoreTest_WhenInitialBoard() {
        // given
        Board board = BoardGenerator.generate();
        Judge judge = new Judge();

        // when
        Map<Camp, Double> result = judge.calculateScore(board);

        // then
        assertThat(result)
                .containsEntry(Camp.CHU, 72.0)
                .containsEntry(Camp.HAN, 73.5);
    }
}
