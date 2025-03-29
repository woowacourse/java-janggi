package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @DisplayName("위치는 행과 열의 위치 정보를 가진다.")
    @Test
    void locationCreate() {
        //given
        final Position position = new Position(4, 5);

        //when - then
        assertThat(position.row()).isEqualTo(4);
        assertThat(position.col()).isEqualTo(5);
    }

    @DisplayName("장기판의 범위를 초과하면 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"10:9", "-1:-1", "-1:8", "9:-1"}, delimiter = ':')
    void validateOutOfBound(final int row, final int col) {
        //when // then
        assertThatThrownBy(() -> new Position(row, col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("현재 위치와 목적 위치의 차가 1이라면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"3:2", "2:3", "1:2", "2:1"}, delimiter = ':')
    void isOneStep(final int row, final int col) {

        //given
        final Position currentPosition = new Position(2, 2);
        final Position targetPosition = new Position(row, col);

        //when
        final boolean actual = currentPosition.isOneStep(targetPosition);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("현재 위치와 목적 위치의 차가 1이 아니라면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"2:2", "4:3", "4:2", "3:3", "1:1"}, delimiter = ':')
    void isNotOneStep(final int row, final int col) {
        //given
        final Position currentPosition = new Position(2, 2);
        final Position targetPosition = new Position(row, col);

        //when
        final boolean actual = currentPosition.isOneStep(targetPosition);

        //then
        assertThat(actual).isFalse();
    }

    @DisplayName("현재 위치와 목적 위치의 Row 값의 절댓값 차를 구한다.")
    @Test
    void calculateDifferenceRow() {
        //given
        final Position currentPosition = new Position(2, 2);
        final Position targetPosition = new Position(4, 2);

        //when
        final int actual = currentPosition.calculateDifferenceRow(targetPosition.row());

        //then
        assertThat(actual).isEqualTo(2);
    }

    @DisplayName("현재 위치와 목적 위치의 col 값의 절댓값 차를 구한다.")
    @Test
    void calculateDifferenceCol() {
        //given
        final Position currentPosition = new Position(2, 2);
        final Position targetPosition = new Position(2, 4);

        //when
        final int actual = currentPosition.calculateDifferenceCol(targetPosition.col());

        //then
        assertThat(actual).isEqualTo(2);
    }

    @DisplayName("자신의 위치를 기준으로 목적지가 뒤에 있다면 true를 반환한다.")
    @Test
    void isBehindWhenOneStepBackwards() {
        // given
        final Position currentPosition = new Position(3, 2);
        final Position targetPosition = new Position(2, 2);

        // when
        final boolean actual = currentPosition.isBehind(targetPosition);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("자신의 위치를 기준으로 목적지가 뒤에 있다면 true를 반환한다.")
    @Test
    void isBehindWhenOneStepBackwards2() {
        // given
        final Position currentPosition = new Position(2, 2);
        final Position targetPosition = new Position(3, 2);

        // when
        final boolean actual = targetPosition.isBehind(currentPosition);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("현재 위치와 목적위치의 Row 차이와 Col 의 차이가 1이라면 한칸 대각선으로 판단한다.")
    @ParameterizedTest
    @CsvSource(value = {"4:4", "2:4", "4:2", "2:2"}, delimiter = ':')
    void isOneDiagonal(final int row, final int col) {
        //given
        final Position currentPosition = new Position(3, 3);
        final Position targetPosition = new Position(row, col);

        //when
        final boolean actual = currentPosition.isOneDiagonal(targetPosition);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("현재 위치와 목적위치의 Row 차이와 Col 의 차이가 1이상이라면 대각선으로 판단한다.")
    @ParameterizedTest
    @CsvSource(value = {"4:2", "1:1", "4:2", "2:2"}, delimiter = ':')
    void isDiagonal(final int row, final int col) {
        //given
        final Position currentPosition = new Position(3, 3);
        final Position targetPosition = new Position(row, col);

        //when
        final boolean actual = currentPosition.isDiagonal(targetPosition);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("현재 위치와 목적위치의 Row 차이와 Col 의 차이가 1이 아니라면 대각선으로 판단하지 않는다.")
    @ParameterizedTest
    @CsvSource(value = {"3:3", "3:5", "5:3", "4:3", "3:2"}, delimiter = ':')
    void isNotOneDiagonal(final int row, final int col) {
        //given
        final Position currentPosition = new Position(3, 3);
        final Position targetPosition = new Position(row, col);

        //when
        final boolean actual = currentPosition.isOneDiagonal(targetPosition);

        //then
        assertThat(actual).isFalse();
    }
}

