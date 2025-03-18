package janggi.board;

import janggi.piece.*;
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
            "0,3", "2,3", "4,3", "6,3", "8,3",
            "0,6", "2,6", "4,6", "6,6", "8,6"
    })
    @DisplayName("졸 초기화 테스트")
    void test2(int row, int col) {
        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(row, col))).isInstanceOf(Soldier.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,0", "6,0", "2,9", "6,9"
    })
    @DisplayName("상 초기화 테스트")
    void test4(int row, int col) {

        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(row, col))).isInstanceOf(Elephant.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,0", "7,0", "1,9", "7,9"
    })
    @DisplayName("마 초기화 테스트")
    void test5(int row, int col) {
        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(row, col))).isInstanceOf(Horse.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,2", "7,2", "1,7", "7,7"
    })
    @DisplayName("마 초기화 테스트")
    void test6(int row, int col) {
        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(row, col))).isInstanceOf(Cannon.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0,0", "8,0", "0,9", "8,9"
    })
    @DisplayName("차 초기화 테스트")
    void test7(int row, int col) {
        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(row, col))).isInstanceOf(Chariot.class);
    }

}
