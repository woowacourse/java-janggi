package janggi.board;

import janggi.piece.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiBoardTest {

    @Test
    @DisplayName("9 x 10의 빈 장기판 생성 테스트")
    void test1() {
        assertThat(JanggiBoard.initialize().getBoard().size()).isEqualTo(90);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0,6", "2,6", "4,6", "6,6", "8,6"
    })
    @DisplayName("초나라 졸 생성 테스트")
    void test2(int row, int col) {
        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(row, col))).isInstanceOf(Soldier.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0,3", "2,3", "4,3", "6,3", "8,3"
    })
    @DisplayName("한나라 졸 생성 테스트")
    void test3(int row, int col) {
        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(row, col))).isInstanceOf(Soldier.class);
    }
}
