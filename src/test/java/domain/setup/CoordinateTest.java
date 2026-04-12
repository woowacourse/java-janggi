package domain.setup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Coordinate 클래스 테스트")
class CoordinateTest {

    @Test
    @DisplayName("toCoordinate: 좌표 입력을 올바르게 파싱한다")
    void fromCoordinateParseMixedCaseInput() {
        Coordinate coord = Coordinate.toCoordinate("E5 f3");

        assertThat(coord.source()).isEqualTo(new Position(Column.E, Row.FIVE));
        assertThat(coord.target()).isEqualTo(new Position(Column.F, Row.THREE));
    }

    @Test
    @DisplayName("올바른 좌표 입력에 대해 Position을 반환한다")
    void fromAndFromReturnCorrectPositions() {
        Coordinate coord = Coordinate.toCoordinate("c3 g7");

        assertThat(coord.source()).isEqualTo(new Position(Column.C, Row.THREE));
        assertThat(coord.target()).isEqualTo(new Position(Column.G, Row.SEVEN));
    }

    @ParameterizedTest(name = "유효하지 않은 입력 '{0}'은 예외를 던진다")
    @ValueSource(strings = {
            "a0",
            "a0b1",
            "a0 b1 c2",
            "",
            " ",
            "j0 a1",
            "a10 b1",
            "a  0 b1",
    })
    @DisplayName("toCoordinate: 유효하지 않은 입력은 예외를 던진다")
    void fromCoordinateThrowsForInvalidInput(String input) {
        assertThatThrownBy(() -> Coordinate.toCoordinate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}
