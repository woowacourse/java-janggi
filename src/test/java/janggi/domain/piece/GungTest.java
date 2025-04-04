package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Pieces;
import janggi.domain.setting.CampType;
import janggi.domain.value.JanggiPosition;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GungTest {

    static final JanggiPosition STANDARD = new JanggiPosition(4, 8);

    @DisplayName("장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test1(JanggiPosition destination) {
        //given
        Gung gung = Gung.generateInitialGung(CampType.CHO).getFirst();

        //when
        Gung movedGung = gung.move(destination, new Pieces(List.of()), new Pieces(List.of()));

        //then
        assertThat(movedGung.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() + 1)),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() - 1))
        );
    }

    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void test2(JanggiPosition destination) {
        //given
        Gung gung = Gung.generateInitialGung(CampType.CHO).getFirst();

        //when & then
        assertThatThrownBy(() -> gung.move(destination, new Pieces(List.of()), new Pieces(List.of())))
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
        Gung gung = Gung.generateInitialGung(CampType.CHO).getFirst();
        JanggiPosition destination = new JanggiPosition(STANDARD.x() + 1, STANDARD.y());
        Gung otherPiece = Gung.from(destination);

        //when & then
        assertThatThrownBy(() -> gung.move(destination, new Pieces(List.of()), new Pieces(List.of(otherPiece))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("적 장기말이 목적지에 있을 때, 잡아서 없애버린다.")
    @Test
    void test4() {
        //given
        Gung gung = Gung.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(5, 8);
        Cha enemyCha = Cha.from(destination);
        Pieces enemyPieces = new Pieces(List.of(enemyCha));

        //when
        Gung movedGung = gung.move(destination, enemyPieces, new Pieces(List.of()));

        //then
        Assertions.assertThat(enemyPieces.isNotBlockedBy(destination)).isTrue();
    }

    @DisplayName("궁안에서 대각선으로 이동 가능하다.")
    @ParameterizedTest
    @MethodSource()
    void test5(JanggiPosition destination) {
        //given
        Gung gung = Gung.from(STANDARD);

        //when
        boolean isMoving = gung.ableToMove(destination, new Pieces(List.of()), new Pieces(List.of()));

        //then
        Assertions.assertThat(isMoving).isTrue();

    }

    static Stream<Arguments> test5() {
        return Stream.of(
                Arguments.of(new JanggiPosition(3, 7)),
                Arguments.of(new JanggiPosition(5, 7)),
                Arguments.of(new JanggiPosition(5, 9)),
                Arguments.of(new JanggiPosition(3, 9))
        );
    }

    @DisplayName("궁 밖으로 나갈 수 없다.")
    @Test
    void test6() {
        //given
        Gung gung = Gung.from(new JanggiPosition(3,8));
        JanggiPosition destination = new JanggiPosition(2,8);
        //when
        boolean isMoving = gung.ableToMove(destination, new Pieces(List.of()), new Pieces(List.of()));

        //then
        Assertions.assertThat(isMoving).isFalse();
    }
}
