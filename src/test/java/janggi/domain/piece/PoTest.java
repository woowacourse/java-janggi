package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.Jol;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Po;
import janggi.domain.value.JanggiPosition;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
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
        Jol jumpPad = Jol.from(jumpPadJanggiPosition);

        //when
        Po movedPo = po.move(destination, new Pieces(List.of()), new Pieces(List.of(jumpPad)));

        //then
        assertThat(movedPo.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y()), new JanggiPosition(8, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y()), new JanggiPosition(0, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() + 1), new JanggiPosition(STANDARD.x(), 9)),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() - 1), new JanggiPosition(STANDARD.x(), 0))
        );
    }


    @DisplayName("장기말의 경로상에 점프대가 없는 경우 이동할 수 없다")
    @ParameterizedTest
    @MethodSource()
    void test2(JanggiPosition destination) {
        //given
        Po po = Po.from(STANDARD);

        //when & then
        assertThatThrownBy(() -> po.move(destination, new Pieces(List.of()), new Pieces(List.of())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test2() {
        return Stream.of(
                Arguments.of(new JanggiPosition(8, STANDARD.y())),
                Arguments.of(new JanggiPosition(0, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x(), 0)),
                Arguments.of(new JanggiPosition(STANDARD.x(), 9))
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
        assertThatThrownBy(() -> po.move(destination, new Pieces(List.of()), new Pieces(List.of(jumpPad))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test3() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y()), new JanggiPosition(8, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y()), new JanggiPosition(0, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() + 1), new JanggiPosition(STANDARD.x(), 0)),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() - 1), new JanggiPosition(STANDARD.x(), 9))
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
        assertThatThrownBy(() -> po.move(destination, new Pieces(List.of(jumpPad)), new Pieces(List.of())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test4() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y()), new JanggiPosition(8, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y()), new JanggiPosition(0, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() + 1), new JanggiPosition(STANDARD.x(), 0)),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() - 1), new JanggiPosition(STANDARD.x(), 9))
        );
    }

    @DisplayName("장기말의 경로상에 적군과 아군 상관없이 점프대가 2개이상인 경우 이동할 수 없다")
    @ParameterizedTest
    @MethodSource()
    void test5(List<JanggiPosition> jumpPadJanggiPositions, JanggiPosition destination) {
        //given
        Po po = Po.from(STANDARD);
        List<Jol> jumpPads = jumpPadJanggiPositions.stream()
                .map(Jol::from)
                .toList();

        //when & then
        assertThatThrownBy(() -> po.move(destination, new Pieces(List.of(jumpPads.getFirst())), new Pieces(List.of(jumpPads.getLast()))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test5() {
        return Stream.of(
                Arguments.of(
                        List.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y()),
                                new JanggiPosition(STANDARD.x() + 2, STANDARD.y())),
                        new JanggiPosition(8, STANDARD.y())),
                Arguments.of(
                        List.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y()),
                                new JanggiPosition(STANDARD.x() - 2, STANDARD.y())),
                        new JanggiPosition(0, STANDARD.y())),
                Arguments.of(
                        List.of(new JanggiPosition(STANDARD.x(), STANDARD.y() + 1),
                                new JanggiPosition(STANDARD.x(), STANDARD.y() + 2)),
                        new JanggiPosition(STANDARD.x(), 0)),
                Arguments.of(
                        List.of(new JanggiPosition(STANDARD.x(), STANDARD.y() - 1),
                                new JanggiPosition(STANDARD.x(), STANDARD.y() - 2)),
                        new JanggiPosition(STANDARD.x(), 9))
        );
    }

    @DisplayName("목적지에 아군 장기말이 있는 경우 이동할 수 없다")
    @Test
    void test6() {
        //given
        Po po = Po.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(8, STANDARD.y());
        Jol jumpPad = Jol.from(destination);

        //when & then
        assertThatThrownBy(() -> po.move(destination, new Pieces(List.of()), new Pieces(List.of(jumpPad))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("목적지에 상대 장기말이 있는 경우 이동할 수 없다")
    @Test
    void test7() {
        //given
        Po po = Po.from(STANDARD);
        Jol jumpPad = Jol.from(new JanggiPosition(STANDARD.x() + 1, STANDARD.y()));
        JanggiPosition destination = new JanggiPosition(8, STANDARD.y());

        //when
        Po movedPo = po.move(destination, new Pieces(List.of(jumpPad)), new Pieces(List.of()));

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
        assertThatThrownBy(() -> po.move(destination, new Pieces(List.of()), new Pieces(List.of())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("적 장기말이 목적지에 있을 때, 잡아서 없애버린다.")
    @Test
    void test9() {
        //given
        Po po = Po.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(4,2);
        JanggiPosition alliesPosition = new JanggiPosition(4,3);
        Jol alliesJol = Jol.from(alliesPosition);
        Jol enemyJol = Jol.from(destination);
        Pieces enemyPieces = new Pieces(List.of(enemyJol));

        //when
        Po movedPo = po.move(destination, enemyPieces, new Pieces(List.of(alliesJol)));

        //then
        Assertions.assertThat(enemyPieces.isNotBlockedBy(destination)).isTrue();
    }

    @DisplayName("포가 궁성내에 있을 때 이동")
    @ParameterizedTest
    @MethodSource()
    void test10(JanggiPosition currentPosition, Jol allies, JanggiPosition  destination) {
        //given
        Po po = Po.from(currentPosition);

        //when
        boolean isMove = po.ableToMove(destination, new Pieces(List.of()), new Pieces(List.of(allies)));

        //then
        Assertions.assertThat(isMove).isTrue();
    }

    static Stream<Arguments> test10() {
        return Stream.of(
                Arguments.of(new JanggiPosition(3,2), Jol.from(new JanggiPosition(3,1)), new JanggiPosition(3,0)),
                Arguments.of(new JanggiPosition(3,2), Jol.from(new JanggiPosition(4,1)),new JanggiPosition(5,0)),
                Arguments.of(new JanggiPosition(3,2), Jol.from(new JanggiPosition(4,2)),new JanggiPosition(5,2)),
                Arguments.of(new JanggiPosition(5,2), Jol.from(new JanggiPosition(5,1)), new JanggiPosition(5,0)),
                Arguments.of(new JanggiPosition(5,2), Jol.from(new JanggiPosition(4,1)),new JanggiPosition(3,0)),
                Arguments.of(new JanggiPosition(5,2), Jol.from(new JanggiPosition(4,2)),new JanggiPosition(3,2)),
                Arguments.of(new JanggiPosition(3,0), Jol.from(new JanggiPosition(4,0)), new JanggiPosition(5,0)),
                Arguments.of(new JanggiPosition(3,0), Jol.from(new JanggiPosition(4,1)),new JanggiPosition(5,2)),
                Arguments.of(new JanggiPosition(3,0), Jol.from(new JanggiPosition(3,1)),new JanggiPosition(3,2)),
                Arguments.of(new JanggiPosition(5,0), Jol.from(new JanggiPosition(4,0)), new JanggiPosition(3,0)),
                Arguments.of(new JanggiPosition(5,0), Jol.from(new JanggiPosition(4,1)),new JanggiPosition(3,2)),
                Arguments.of(new JanggiPosition(5,0), Jol.from(new JanggiPosition(5,1)),new JanggiPosition(5,2))
        );
    }

    @DisplayName("포가 궁성내에 있을 경우 못 움직이는 경우 확인")
    @ParameterizedTest
    @MethodSource()
    void test11(JanggiPosition currentPosition, Jol allies, JanggiPosition  destination) {
        //given
        Po po = Po.from(currentPosition);

        //when
        boolean isMoved = po.ableToMove(destination, new Pieces(List.of()), new Pieces(List.of(allies)));

        //then
        Assertions.assertThat(isMoved).isFalse();
    }

    static Stream<Arguments> test11() {
        return Stream.of(
                Arguments.of(new JanggiPosition(4,0), Jol.from(new JanggiPosition(3,1)), new JanggiPosition(2,2)),
                Arguments.of(new JanggiPosition(4,0), Jol.from(new JanggiPosition(5,1)), new JanggiPosition(6,2)),
                Arguments.of(new JanggiPosition(3,1), Jol.from(new JanggiPosition(4,2)), new JanggiPosition(5,3)),
                Arguments.of(new JanggiPosition(3,1), Jol.from(new JanggiPosition(2,2)), new JanggiPosition(1,3)),
                Arguments.of(new JanggiPosition(5,1), Jol.from(new JanggiPosition(4,2)), new JanggiPosition(3,3)),
                Arguments.of(new JanggiPosition(5,1), Jol.from(new JanggiPosition(6,2)), new JanggiPosition(7,3)),
                Arguments.of(new JanggiPosition(4,2), Jol.from(new JanggiPosition(3,1)), new JanggiPosition(2,0)),
                Arguments.of(new JanggiPosition(4,2), Jol.from(new JanggiPosition(5,1)), new JanggiPosition(6,0)),
                Arguments.of(new JanggiPosition(4,2), Jol.from(new JanggiPosition(3,3)), new JanggiPosition(2,4)),
                Arguments.of(new JanggiPosition(4,2), Jol.from(new JanggiPosition(5,3)), new JanggiPosition(6,4))
        );
    }
}

