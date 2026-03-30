package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    @DisplayName("좌표 문자열 두 개를 받아 Position을 생성한다.")
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
    @DisplayName("좌표 입력이 두 개가 아니면 예외가 발생한다.")
    void throwWhenInputSizeIsNotTwo() {
        // given
        List<String> rawPosition = List.of("3");

        // when & then
        assertThatThrownBy(() -> Position.makePosition(rawPosition))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("기물의 좌표는 두 개로 입력해야 합니다.");
    }

    @Test
    @DisplayName("좌표 입력이 숫자가 아니면 예외가 발생한다.")
    void throwWhenInputContainsNonNumericValue() {
        // given
        List<String> rawPosition = List.of("a", "7");

        // when & then
        assertThatThrownBy(() -> Position.makePosition(rawPosition))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("좌표는 숫자가 입력되어야 합니다.");
    }

    @Test
    @DisplayName("델타만큼 이동한 새 Position을 반환한다.")
    void move() {
        // given
        Position position = new Position(4, 4);

        // when
        Position movedPosition = position.move(Delta.createRightUp());

        // then
        assertAll(
            () -> assertThat(movedPosition).isEqualTo(new Position(5, 5)),
            () -> assertThat(position).isEqualTo(new Position(4, 4))
        );
    }

    @Test
    @DisplayName("같은 좌표의 Position은 동등하고 해시코드도 같다.")
    void equalsAndHashCode() {
        // given
        Position first = new Position(2, 8);
        Position second = new Position(2, 8);
        Position other = new Position(8, 2);

        // when & then
        assertAll(
            () -> assertThat(first).isEqualTo(second),
            () -> assertThat(first.hashCode()).isEqualTo(second.hashCode()),
            () -> assertThat(first).isNotEqualTo(other)
        );
    }
}
