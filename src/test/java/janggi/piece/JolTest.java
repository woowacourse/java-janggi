package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JolTest {

    @DisplayName("졸병은 이름과 위치 정보를 가진다,")
    @Test
    void jolByeongBoardPosition() {
        //given
        Position position = new Position(4, 5);

        //when
        Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), position);

        //then
        assertThat(jol.getBoardPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("jolNonIsMovePositionProvider")
    void nonIsMove(Position position) {
        //given
        Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new Position(5, 5));

        //when //then
        assertThatThrownBy(() -> jol.isMove(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("자신의 위치를 기준으로 뒤를 제외한 가로,세로 한칸 이동이 가능하다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("jolIsMovePositionProvider")
    void isMove(Position position) {
        //given
        Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new Position(5, 5));

        //when
        boolean actual = jol.isMove(position);

        //then
        assertThat(actual).isTrue();
    }


    @DisplayName("졸은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new Position(5, 5));
        Position futurePosition = new Position(4, 5);

        //when
        List<Position> actual = jol.makeRoute(futurePosition);

        //then
        assertThat(actual.contains(futurePosition)).isTrue();
    }

    private static Stream<Arguments> jolNonIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(6, 5)),
                Arguments.of(new Position(6, 3)),
                Arguments.of(new Position(6, 6)));
    }

    private static Stream<Arguments> jolIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(5, 4)),
                Arguments.of(new Position(5, 6)),
                Arguments.of(new Position(4, 5)));
    }
}
