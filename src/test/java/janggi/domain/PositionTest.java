package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @ParameterizedTest
    @DisplayName("기물의 X 좌표 범위는 1~9, Y 좌표 범위는 1~10 이다.")
    @CsvSource({
            "1,1",
            "4,6",
            "7,10",
            "9,10"
    })
    void testPositionWithinValidXYRange(int x, int y) {
        assertThat(new Position(x, y))
                .isInstanceOf(Position.class);
    }

    @ParameterizedTest
    @DisplayName("기물의 X 좌표가 1~9 사이가 아니면 예외가 발생한다.")
    @CsvSource({
            "0,1",
            "10,5",
            "15,8"
    })
    void testPositionWithinValidXRange(int x, int y) {
        assertThatThrownBy(() -> new Position(x, y))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("기물의 Y 좌표가 1~10 사이가 아니면 예외가 발생한다.")
    @CsvSource({
            "1,0",
            "4,11",
            "7,15"
    })
    void testPositionWithinValidYRange(int x, int y) {
        assertThatThrownBy(() -> new Position(x, y))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("같은 X,Y 좌표를 가진 Position은 서로 동등하다.")
    @CsvSource({
            "1, 1, 1, 1",
            "1, 10, 1, 10",
            "9, 10, 9, 10"
    })
    void testPositionEquality(int x1, int y1, int x2, int y2) {
        Position p1 = new Position(x1, y1);
        Position p2 = new Position(x2, y2);

        assertThat(p1).isEqualTo(p2);
    }
}
