package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SangTest {

    static final Position STANDARD = new Position(4, 4);

    @DisplayName("장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test1(Position destination) {
        //given
        Sang sang = Sang.from(STANDARD);

        //when
        Sang movedSang = sang.move(destination, List.of(), List.of());

        //then
        assertThat(movedSang.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(new Position(STANDARD.getX() - 3, STANDARD.getY() - 2)),
                Arguments.of(new Position(STANDARD.getX() - 3, STANDARD.getY() + 2)),
                Arguments.of(new Position(STANDARD.getX() + 3, STANDARD.getY() - 2)),
                Arguments.of(new Position(STANDARD.getX() + 3, STANDARD.getY() + 2)),
                Arguments.of(new Position(STANDARD.getX() - 2, STANDARD.getY() - 3)),
                Arguments.of(new Position(STANDARD.getX() + 2, STANDARD.getY() - 3)),
                Arguments.of(new Position(STANDARD.getX() - 2, STANDARD.getY() + 3)),
                Arguments.of(new Position(STANDARD.getX() + 2, STANDARD.getY() + 3))
        );
    }


    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void test2(Position destination) {
        //given
        Sang sang = Sang.from(STANDARD);

        //when & then
        assertThatThrownBy(() -> sang.move(destination, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test2() {
        return Stream.of(
                Arguments.of(new Position(STANDARD.getX() + 1, STANDARD.getY() + 1)),
                Arguments.of(new Position(STANDARD.getX() + 1, STANDARD.getY() - 1)),
                Arguments.of(new Position(STANDARD.getX() - 1, STANDARD.getY() + 1)),
                Arguments.of(new Position(STANDARD.getX() - 1, STANDARD.getY() - 1))
        );
    }

    @DisplayName("아군 장기말이 경로안에 장애물로 있을 경우 이동이 불가능하다.")
    @ParameterizedTest
    @MethodSource()
    void test4(Position hurdlePosition, Position destination) {
        //given
        Sang sang = Sang.from(STANDARD);
        Sang hurdle = Sang.from(hurdlePosition);

        //when & then
        assertAll(
                () -> assertThatThrownBy(() -> sang.move(destination, List.of(), List.of(hurdle)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> sang.move(destination, List.of(), List.of(hurdle)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    static Stream<Arguments> test4() {
        return Stream.of(
                Arguments.of(new Position(5, 4), new Position(7, 2)),
                Arguments.of(new Position(5, 4), new Position(7, 6)),
                Arguments.of(new Position(6, 3), new Position(7, 2)),
                Arguments.of(new Position(6, 5), new Position(7, 6))
        );
    }

    @DisplayName("아군 장기말이 목적지에 장애물로 있을 경우 이동이 불가능하다.")
    @Test
    void test5() {
        //given
        Sang sang = Sang.from(STANDARD);
        Position hurdle = new Position(7, 2);

        //when & then
        assertThatThrownBy(() -> sang.move(hurdle, List.of(), List.of(Ma.from(hurdle))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("상대 장기말이 경로안에 장애물로 있을 경우 이동이 불가능하다.")
    @ParameterizedTest
    @MethodSource()
    void test6(Position hurdlePosition, Position destination) {
        //given
        Sang sang = Sang.from(STANDARD);
        Sang hurdle = Sang.from(hurdlePosition);

        //when & then
        assertAll(
                () -> assertThatThrownBy(() -> sang.move(destination, List.of(hurdle), List.of()))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> sang.move(destination, List.of(hurdle), List.of()))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    static Stream<Arguments> test6() {
        return Stream.of(
                Arguments.of(new Position(5, 4), new Position(7, 2)),
                Arguments.of(new Position(5, 4), new Position(7, 6)),
                Arguments.of(new Position(6, 3), new Position(7, 2)),
                Arguments.of(new Position(6, 5), new Position(7, 6))
        );
    }

    @DisplayName("상대 장기말이 목적지에 있을 경우 이동이 가능하다.")
    @Test
    void test7() {
        //given
        Sang sang = Sang.from(STANDARD);
        Position hurdle = new Position(7, 2);

        //when
        Sang movedSang = sang.move(hurdle, List.of(Sang.from(hurdle)), List.of());

        //then
        Assertions.assertThat(movedSang.getPosition()).isEqualTo(hurdle);
    }
}