package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PoTest {

    private static final JanggiPosition STANDARD = new JanggiPosition(4, 4);

    @DisplayName("장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test1(JanggiPosition jumpPadJanggiPosition, JanggiPosition destination) {
        //given
        Po po = Po.from(STANDARD);
        Jol jumpPad = Jol.from(jumpPadJanggiPosition, CampType.CHO);

        //when
        Po movedPo = po.move(destination, List.of(), List.of(jumpPad));

        //then
        assertThat(movedPo.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.getX() + 1, STANDARD.getY()), new JanggiPosition(8, STANDARD.getY())),
                Arguments.of(new JanggiPosition(STANDARD.getX() - 1, STANDARD.getY()), new JanggiPosition(0, STANDARD.getY())),
                Arguments.of(new JanggiPosition(STANDARD.getX(), STANDARD.getY() + 1), new JanggiPosition(STANDARD.getX(), 9)),
                Arguments.of(new JanggiPosition(STANDARD.getX(), STANDARD.getY() - 1), new JanggiPosition(STANDARD.getX(), 0))
        );
    }


    @DisplayName("장기말의 경로상에 점프대가 없는 경우 이동할 수 없다")
    @ParameterizedTest
    @MethodSource()
    void test2(JanggiPosition destination) {
        //given
        Po po = Po.from(STANDARD);

        //when & then
        assertThatThrownBy(() -> po.move(destination, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test2() {
        return Stream.of(
                Arguments.of(new JanggiPosition(8, STANDARD.getY())),
                Arguments.of(new JanggiPosition(0, STANDARD.getY())),
                Arguments.of(new JanggiPosition(STANDARD.getX(), 0)),
                Arguments.of(new JanggiPosition(STANDARD.getX(), 9))
        );
    }

    @DisplayName("장기말의 경로상에 아군 포가 있는 경우 이동할 수 없다")
    @ParameterizedTest
    @MethodSource()
    void test3(JanggiPosition poJanggiPosition, JanggiPosition destination) {
        //given
        Po po = Po.from(STANDARD);
        Po jumpPad = Po.from(poJanggiPosition);

        //when & then
        assertThatThrownBy(() -> po.move(destination, List.of(), List.of(jumpPad)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test3() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.getX() + 1, STANDARD.getY()), new JanggiPosition(8, STANDARD.getY())),
                Arguments.of(new JanggiPosition(STANDARD.getX() - 1, STANDARD.getY()), new JanggiPosition(0, STANDARD.getY())),
                Arguments.of(new JanggiPosition(STANDARD.getX(), STANDARD.getY() + 1), new JanggiPosition(STANDARD.getX(), 0)),
                Arguments.of(new JanggiPosition(STANDARD.getX(), STANDARD.getY() - 1), new JanggiPosition(STANDARD.getX(), 9))
        );
    }

    @DisplayName("장기말의 경로상에 적군 포가 있는 경우 이동할 수 없다")
    @ParameterizedTest
    @MethodSource()
    void test4(JanggiPosition poJanggiPosition, JanggiPosition destination) {
        //given
        Po po = Po.from(STANDARD);
        Po jumpPad = Po.from(poJanggiPosition);

        //when & then
        assertThatThrownBy(() -> po.move(destination, List.of(jumpPad), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test4() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.getX() + 1, STANDARD.getY()), new JanggiPosition(8, STANDARD.getY())),
                Arguments.of(new JanggiPosition(STANDARD.getX() - 1, STANDARD.getY()), new JanggiPosition(0, STANDARD.getY())),
                Arguments.of(new JanggiPosition(STANDARD.getX(), STANDARD.getY() + 1), new JanggiPosition(STANDARD.getX(), 0)),
                Arguments.of(new JanggiPosition(STANDARD.getX(), STANDARD.getY() - 1), new JanggiPosition(STANDARD.getX(), 9))
        );
    }


    @DisplayName("장기말의 경로상에 적군과 아군 상관없이 점프대가 2개이상인 경우 이동할 수 없다")
    @ParameterizedTest
    @MethodSource()
    void test5(List<JanggiPosition> jumpPadJanggiPositions, JanggiPosition destination) {
        //given
        Po po = Po.from(STANDARD);
        List<Jol> jumpPads = jumpPadJanggiPositions.stream()
                .map(position -> Jol.from(position, CampType.CHO))
                .toList();

        //when & then
        assertThatThrownBy(() -> po.move(destination, List.of(jumpPads.getFirst()), List.of(jumpPads.getLast())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test5() {
        return Stream.of(
                Arguments.of(
                        List.of(new JanggiPosition(STANDARD.getX() + 1, STANDARD.getY()),
                                new JanggiPosition(STANDARD.getX() + 2, STANDARD.getY())),
                        new JanggiPosition(8, STANDARD.getY())),
                Arguments.of(
                        List.of(new JanggiPosition(STANDARD.getX() - 1, STANDARD.getY()),
                                new JanggiPosition(STANDARD.getX() - 2, STANDARD.getY())),
                        new JanggiPosition(0, STANDARD.getY())),
                Arguments.of(
                        List.of(new JanggiPosition(STANDARD.getX(), STANDARD.getY() + 1),
                                new JanggiPosition(STANDARD.getX(), STANDARD.getY() + 2)),
                        new JanggiPosition(STANDARD.getX(), 0)),
                Arguments.of(
                        List.of(new JanggiPosition(STANDARD.getX(), STANDARD.getY() - 1),
                                new JanggiPosition(STANDARD.getX(), STANDARD.getY() - 2)),
                        new JanggiPosition(STANDARD.getX(), 9))
        );
    }

    @DisplayName("목적지에 아군 장기말이 있는 경우 이동할 수 없다")
    @Test
    void test6() {
        //given
        Po po = Po.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(8, STANDARD.getY());
        Jol jumpPad = Jol.from(destination, CampType.CHO);

        //when & then
        assertThatThrownBy(() -> po.move(destination, List.of(), List.of(jumpPad)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("목적지에 상대 장기말이 있는 경우 이동할 수 없다")
    @Test
    void test7() {
        //given
        Po po = Po.from(STANDARD);
        Jol jumpPad = Jol.from(new JanggiPosition(STANDARD.getX() + 1, STANDARD.getY()), CampType.CHO);
        JanggiPosition destination = new JanggiPosition(8, STANDARD.getY());

        //when
        Po movedPo = po.move(destination, List.of(jumpPad), List.of());

        //then
        assertThat(movedPo.getPosition()).isEqualTo(destination);
    }

    @DisplayName("목적지가 일직선 상에 없는 경우 예외를 발생시킨다.")
    @Test
    void test8() {
        //given
        Po po = Po.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(5, 7);

        //when & then
        assertThatThrownBy(() -> po.move(destination, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }
}