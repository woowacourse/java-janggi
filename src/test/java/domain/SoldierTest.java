package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierTest {

    @Test
    @DisplayName("말을 움직였을 때 해당 위치로 정확히 움직였는지 확인한다.")
    void movePieceTest() {
        // given
        Position currentPosition = Position.of(0, 0);
        Position movePosition = Position.of(1, 1);
        Piece solider = new Soldier(currentPosition, TeamType.CHO);

        // when
        solider.moveTo(movePosition);

        // then
        Position position = solider.getPosition();
        assertThat(position).isEqualTo(movePosition);
    }

    static Stream<Arguments> canMoveSoldier() {
        return Stream.of(
                Arguments.of(Position.of(1, 0), true),
                Arguments.of(Position.of(2, 1), true),
                Arguments.of(Position.of(1, 2), true),
                Arguments.of(Position.of(0, 1), false),
                Arguments.of(Position.of(2, 2), false),
                Arguments.of(Position.of(0, 2), false),
                Arguments.of(Position.of(0, 0), false),
                Arguments.of(Position.of(2, 0), false)
        );
    }

    @ParameterizedTest
    @MethodSource()
    @DisplayName("주위 칸이 비어있을 때 정상적으로 이동할 수 있다")
    void canMoveSoldier(Position movePosition, boolean expected) {
        // given
        Position currentPosition = Position.of(1, 1);
        Piece solider = new Soldier(currentPosition, TeamType.CHO);

        // when
        boolean actual = solider.canMove(movePosition, List.of());

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("주위 칸에 아군이 있으면 이동할 수 없다.")
    void canMoveSoldier2() {
        // given
        Position movePosition = Position.of(2,1);
        Position position = Position.of(2,1);
        Piece solider1 = new Soldier(position,TeamType.CHO);

        Position currentPosition = Position.of(1, 1);
        Piece solider2 = new Soldier(currentPosition, TeamType.CHO);

        // when
        boolean actual = solider2.canMove(movePosition, List.of(solider1));

        // then
        assertThat(actual).isFalse();
    }
}
