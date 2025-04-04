package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.piece.Jol;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Sang;
import janggi.domain.value.JanggiPosition;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SangTest {

    static final JanggiPosition STANDARD = new JanggiPosition(4, 4);

    @DisplayName("장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test1(JanggiPosition destination) {
        //given
        Sang sang = Sang.from(STANDARD);

        //when
        Sang movedSang = sang.move(destination, new Pieces(List.of()), new Pieces(List.of()));

        //then
        assertThat(movedSang.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.x() - 3, STANDARD.y() - 2)),
                Arguments.of(new JanggiPosition(STANDARD.x() - 3, STANDARD.y() + 2)),
                Arguments.of(new JanggiPosition(STANDARD.x() + 3, STANDARD.y() - 2)),
                Arguments.of(new JanggiPosition(STANDARD.x() + 3, STANDARD.y() + 2)),
                Arguments.of(new JanggiPosition(STANDARD.x() - 2, STANDARD.y() - 3)),
                Arguments.of(new JanggiPosition(STANDARD.x() + 2, STANDARD.y() - 3)),
                Arguments.of(new JanggiPosition(STANDARD.x() - 2, STANDARD.y() + 3)),
                Arguments.of(new JanggiPosition(STANDARD.x() + 2, STANDARD.y() + 3))
        );
    }

    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void test2(JanggiPosition destination) {
        //given
        Sang sang = Sang.from(STANDARD);

        //when & then
        assertThatThrownBy(() -> sang.move(destination, new Pieces(List.of()), new Pieces(List.of())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당하는 대각선 경로가 없습니다.");
    }

    static Stream<Arguments> test2() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y() + 1)),
                Arguments.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y() - 1)),
                Arguments.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y() + 1)),
                Arguments.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y() - 1))
        );
    }

    @DisplayName("아군 장기말이 경로안에 장애물로 있을 경우 이동이 불가능하다.")
    @ParameterizedTest
    @MethodSource()
    void test4(JanggiPosition hurdleJanggiPosition, JanggiPosition destination) {
        //given
        Sang sang = Sang.from(STANDARD);
        Sang hurdle = Sang.from(hurdleJanggiPosition);

        //when & then
        assertAll(
                () -> assertThatThrownBy(() -> sang.move(destination, new Pieces(List.of()), new Pieces(List.of(hurdle))))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> sang.move(destination, new Pieces(List.of()), new Pieces(List.of(hurdle))))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    static Stream<Arguments> test4() {
        return Stream.of(
                Arguments.of(new JanggiPosition(5, 4), new JanggiPosition(7, 2)),
                Arguments.of(new JanggiPosition(5, 4), new JanggiPosition(7, 6)),
                Arguments.of(new JanggiPosition(6, 3), new JanggiPosition(7, 2)),
                Arguments.of(new JanggiPosition(6, 5), new JanggiPosition(7, 6))
        );
    }

    @DisplayName("아군 장기말이 목적지에 장애물로 있을 경우 이동이 불가능하다.")
    @Test
    void test5() {
        //given
        Sang sang = Sang.from(STANDARD);
        JanggiPosition hurdle = new JanggiPosition(7, 2);

        //when & then
        assertThatThrownBy(() -> sang.move(hurdle, new Pieces(List.of()), new Pieces(List.of(Ma.from(hurdle)))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("상대 장기말이 경로안에 장애물로 있을 경우 이동이 불가능하다.")
    @ParameterizedTest
    @MethodSource()
    void test6(JanggiPosition hurdleJanggiPosition, JanggiPosition destination) {
        //given
        Sang sang = Sang.from(STANDARD);
        Sang hurdle = Sang.from(hurdleJanggiPosition);

        //when & then
        assertAll(
                () -> assertThatThrownBy(() -> sang.move(destination, new Pieces(List.of(hurdle)), new Pieces(List.of())))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> sang.move(destination, new Pieces(List.of(hurdle)), new Pieces(List.of())))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    static Stream<Arguments> test6() {
        return Stream.of(
                Arguments.of(new JanggiPosition(5, 4), new JanggiPosition(7, 2)),
                Arguments.of(new JanggiPosition(5, 4), new JanggiPosition(7, 6)),
                Arguments.of(new JanggiPosition(6, 3), new JanggiPosition(7, 2)),
                Arguments.of(new JanggiPosition(6, 5), new JanggiPosition(7, 6))
        );
    }

    @DisplayName("상대 장기말이 목적지에 있을 경우 이동이 가능하다.")
    @Test
    void test7() {
        //given
        Sang sang = Sang.from(STANDARD);
        JanggiPosition hurdle = new JanggiPosition(7, 2);

        //when
        Sang movedSang = sang.move(hurdle, new Pieces(List.of(Sang.from(hurdle))), new Pieces(List.of()));

        //then
        Assertions.assertThat(movedSang.getPosition()).isEqualTo(hurdle);
    }

    @DisplayName("적 장기말이 목적지에 있을 때, 잡아서 없애버린다.")
    @Test
    void test8() {
        //given
        Sang sang = Sang.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(6,1);
        Jol enemyJol = Jol.from(destination);
        Pieces enemyPieces = new Pieces(List.of(enemyJol));

        //when
        Sang movedSang = sang.move(destination, enemyPieces, new Pieces(List.of()));

        //then
        Assertions.assertThat(enemyPieces.isNotBlockedBy(destination)).isTrue();
    }
}