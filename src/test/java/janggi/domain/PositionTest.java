package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.common.ErrorMessage;
import janggi.domain.move.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @DisplayName("검증 테스트")
    @Test
    void test1() {
        assertThatCode(() -> Position.of(1, 2))
                .doesNotThrowAnyException();
    }

    @DisplayName("잘못된 좌표를 입력하면 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"0,9", "11,0", "1,10", "1,0", "-1,1"}, delimiter = ',')
    void test2(int row, int column) {
        assertThatThrownBy(() -> Position.of(row, column))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_BOARD_POSITION.getMessage());
    }

    @DisplayName("한나라에서 대각선으로 움직일 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1,4", "1,6", "2,5", "3,4", "3,6", "8,4", "8,6", "9,5", "10,4", "10,6"}, delimiter = ',')
    void test3(int row, int column) {

        assertThat(Position.of(row, column).canCrossMove()).isTrue();
    }

    @DisplayName("궁성 내부라면 true를 반환한다.")
    @Test
    void test4() {
        for (int row = 1; row <= 3; row++) {
            for (int column = 4; column <= 6; column++) {
                assertThat(Position.of(row, column).isPalace()).isTrue();
            }
        }

        for (int row = 8; row <= 10; row++) {
            for (int column = 4; column <= 6; column++) {
                assertThat(Position.of(row, column).isPalace()).isTrue();
            }
        }
    }

    @DisplayName("궁성 내부가 아니라면 false를 반환한다.")
    @Test
    void test5() {
        assertThat(Position.of(1, 1).isPalace()).isFalse();
    }

}
