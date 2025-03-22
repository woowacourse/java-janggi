package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChaTest {

    @DisplayName("차은 위치 정보를 가진다,")
    @Test
    void chaBoardPosition() {
        //given
        Position position = new Position(4, 5);

        //when
        Cha cha = new Cha(new PieceProfile("차", Nation.HAN), position);

        //then
        assertThat(cha.getBoardPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @Test
    void nonIsMove() {
        //given
        Cha cha = new Cha(new PieceProfile("차", Nation.HAN), new Position(0, 0));

        //when //then
        assertThatThrownBy(() -> cha.isMove(new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("차는 움직임을 자신의 위치를 기준으로 가로, 세로 방향으로 무제한 이동할 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("chaIsMovePositionProvider")
    void isMove(Position position) {
        //given
        Cha cha = new Cha(new PieceProfile("차", Nation.HAN), new Position(0, 0));

        //when
        boolean actual = cha.isMove(position);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("차는 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Cha cha = new Cha(new PieceProfile("차", Nation.HAN), new Position(5, 5));
        Position futurePosition = new Position(0, 5);

        //when
        List<Position> actual = cha.makeRoute(futurePosition);

        //then
        assertThat(actual).containsExactly(
                new Position(4, 5),
                new Position(3, 5),
                new Position(2, 5),
                new Position(1, 5),
                new Position(0, 5)
        );
    }

    private static Stream<Arguments> chaIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(0, 1)),
                Arguments.of(new Position(1, 0)));
    }
}
