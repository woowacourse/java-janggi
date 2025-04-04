package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.piece.Jol;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Pieces;
import janggi.domain.value.JanggiPosition;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MaTest {

    static final JanggiPosition STANDARD = new JanggiPosition(4, 4);

    @DisplayName("장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test1(JanggiPosition destination) {
        //given
        Ma ma = Ma.from(STANDARD);

        //when
        Ma movedMa = ma.move(destination, new Pieces(List.of()), new Pieces(List.of()));

        //then
        assertThat(movedMa.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.x() + 2, STANDARD.y() + 1)),
                Arguments.of(new JanggiPosition(STANDARD.x() + 2, STANDARD.y() - 1)),
                Arguments.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y() - 2)),
                Arguments.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y() + 2)),
                Arguments.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y() - 2)),
                Arguments.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y() + 2)),
                Arguments.of(new JanggiPosition(STANDARD.x() - 2, STANDARD.y() - 1)),
                Arguments.of(new JanggiPosition(STANDARD.x() - 2, STANDARD.y() + 1))
        );
    }


    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void test2(JanggiPosition destination) {
        //given
        Ma ma = Ma.from(STANDARD);

        //when & then
        assertThatThrownBy(() -> ma.move(destination, new Pieces(List.of()), new Pieces(List.of())))
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

    @DisplayName("아군 장기말이 직진 경로안에 장애물로 있을 경우 이동이 불가능하다.")
    @Test
    void test4() {
        //given
        Ma ma = Ma.from(STANDARD);
        JanggiPosition hurdleJanggiPosition = new JanggiPosition(5, 4);
        Ma hurdle = Ma.from(hurdleJanggiPosition);

        //when & then
        assertAll(
                () -> assertThatThrownBy(() -> ma.move(new JanggiPosition(6, 3), new Pieces(List.of()), new Pieces(List.of(hurdle))))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> ma.move(new JanggiPosition(6, 5), new Pieces(List.of()), new Pieces(List.of(hurdle))))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    @DisplayName("아군 장기말이 목적지에 장애물로 있을 경우 이동이 불가능하다.")
    @Test
    void test5() {
        //given
        Ma ma = Ma.from(STANDARD);
        JanggiPosition hurdle = new JanggiPosition(6, 3);

        //when & then
        assertThatThrownBy(() -> ma.move(hurdle, new Pieces(List.of()), new Pieces(List.of(Ma.from(hurdle)))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("상대 장기말이 경로에 장애물로 있을 경우 이동이 불가능하다.")
    @Test
    void test6() {
        //given
        Ma ma = Ma.from(STANDARD);
        JanggiPosition hurdleJanggiPosition = new JanggiPosition(5, 4);
        Ma hurdle = Ma.from(hurdleJanggiPosition);

        //when & then
        assertAll(
                () -> assertThatThrownBy(() -> ma.move(new JanggiPosition(6, 3), new Pieces(List.of(hurdle)), new Pieces(List.of())))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> ma.move(new JanggiPosition(6, 5), new Pieces(List.of(hurdle)), new Pieces(List.of())))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    @DisplayName("상대 장기말이 목적지에 있을 경우 이동이 가능하다.")
    @Test
    void test7() {
        //given
        Ma ma = Ma.from(STANDARD);
        JanggiPosition hurdle = new JanggiPosition(6, 3);

        //when
        Ma movedMa = ma.move(hurdle, new Pieces(List.of(Ma.from(hurdle))), new Pieces(List.of()));

        //then
        Assertions.assertThat(movedMa.getPosition()).isEqualTo(hurdle);
    }

    @DisplayName("적 장기말이 목적지에 있을 때, 잡아서 없애버린다.")
    @Test
    void test8() {
        //given
        Ma ma = Ma.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(5,2);
        Jol enemyJol = Jol.from(destination);
        Pieces enemyPieces = new Pieces(List.of(enemyJol));

        //when
        Ma movedMa = ma.move(destination, enemyPieces, new Pieces(List.of()));

        //then
        Assertions.assertThat(enemyPieces.isNotBlockedBy(destination)).isTrue();
    }

}
