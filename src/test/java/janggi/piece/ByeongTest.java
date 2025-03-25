package janggi.piece;

import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ByeongTest {

    static final JanggiPosition STANDARD = new JanggiPosition(4, 4);

    @DisplayName("한의 장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test1(JanggiPosition destination) {
        //given
        Byeong byeong = Byeong.from(STANDARD);

        //when
        Byeong movedByeong = byeong.move(destination, new Pieces(List.of()), new Pieces(List.of()));

        //then
        assertThat(movedByeong.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() + 1))
        );
    }

    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void test2(JanggiPosition destination) {
        //given
        Byeong byeong = Byeong.from(STANDARD);

        //when & then
        assertThatThrownBy(() -> byeong.move(destination, new Pieces(List.of()), new Pieces(List.of())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test2() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.x() + 2, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x() - 2, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() - 2))
        );
    }

    @DisplayName("아군 장기말이 장애물일 경우 해당 위치로 이동이 불가능하다.")
    @Test
    void test3() {
        //given
        Byeong byeong = Byeong.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(4, 3);
        Byeong otherPiece = Byeong.from(destination);

        //when & then
        assertThatThrownBy(() -> byeong.move(destination, new Pieces(List.of()), new Pieces(List.of(otherPiece))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("상대 장기말이 장애물일 경우 해당 위치로 이동이 가능하다.")
    @Test
    void test4() {
        //given
        Byeong byeong = Byeong.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(4, 5);
        Byeong otherPiece = Byeong.from(destination);

        //when
        Byeong movedByeong = byeong.move(destination, new Pieces(List.of(otherPiece)), new Pieces(List.of()));

        //then
        Assertions.assertThat(movedByeong.getPosition()).isEqualTo(destination);
    }

}