package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @Test
    @DisplayName("좌표 문자열 두 개를 받아 Position 생성 성공")
    void makePosition() {
        // given
        List<String> rawPosition = List.of("3", "7");

        // when
        Position position = Position.makePosition(rawPosition);

        // then
        assertAll(
            () -> assertThat(position.getX()).isEqualTo(3),
            () -> assertThat(position.getY()).isEqualTo(7)
        );
    }

    @Test
    @DisplayName("좌표 입력이 두 개가 아닐 경우 예외 발생")
    void throwWhenInputSizeIsNotTwo() {
        // given
        List<String> rawPosition = List.of("3");

        // when & then
        assertThatThrownBy(() -> Position.makePosition(rawPosition))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("기물의 좌표는 두 개로 입력해야 합니다.");
    }

    @Test
    @DisplayName("좌표 입력이 숫자가 아닐 경우 예외 발생")
    void throwWhenInputContainsNonNumericValue() {
        // given
        List<String> rawPosition = List.of("a", "7");

        // when & then
        assertThatThrownBy(() -> Position.makePosition(rawPosition))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("좌표는 숫자가 입력되어야 합니다.");
    }

    @ParameterizedTest
    @DisplayName("입력한 좌표가 범위 밖일 경우 예외 발생")
    @CsvSource({
            "0, 6",
            "10, 6",
            "6, 0",
            "6, 11",
    })
    void makePosition_fail_when_out_of_range(String x, String y) {
        // given
        List<String> parsedPiecePosition = List.of(x, y);

        // when & then
        assertThatThrownBy(() -> Position.makePosition(parsedPiecePosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력한 좌표가 장기판 범위 밖입니다. x : " + x + ", y : " + y);
    }

    @Test
    @DisplayName("델타만큼 이동한 새 Position을 반환한다.")
    void move() {
        // given
        Position position = new Position(4, 4);

        // when
        Position movedPosition = position.move(Delta.RIGHT_UP);

        // then
        assertAll(
            () -> assertThat(movedPosition).isEqualTo(new Position(5, 5)),
            () -> assertThat(position).isEqualTo(new Position(4, 4))
        );
    }
}
