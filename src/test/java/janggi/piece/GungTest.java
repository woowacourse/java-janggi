package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GungTest {

    static final Position STANDARD = new Position(4, 8);

    @DisplayName("장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test1(Position destination) {
        //given
        Gung gung = Gung.generateInitialGung(CampType.CHO).getFirst();

        //when
        Gung movedGung = gung.move(destination, List.of(), List.of());

        //then
        assertThat(movedGung.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(new Position(STANDARD.getX() + 1, STANDARD.getY())),
                Arguments.of(new Position(STANDARD.getX() - 1, STANDARD.getY())),
                Arguments.of(new Position(STANDARD.getX(), STANDARD.getY() + 1)),
                Arguments.of(new Position(STANDARD.getX(), STANDARD.getY() - 1))
        );
    }


    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void test2(Position destination) {
        //given
        Gung gung = Gung.generateInitialGung(CampType.CHO).getFirst();

        //when & then
        assertThatThrownBy(() -> gung.move(destination, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test2() {
        return Stream.of(
                Arguments.of(new Position(STANDARD.getX() + 2, STANDARD.getY())),
                Arguments.of(new Position(STANDARD.getX() - 2, STANDARD.getY())),
                Arguments.of(new Position(STANDARD.getX(), STANDARD.getY() + 2)),
                Arguments.of(new Position(STANDARD.getX(), STANDARD.getY() - 2))
        );
    }

    @DisplayName("아군 장기말이 장애물일 경우 해당 위치로 이동이 불가능하다.")
    @Test
    void test3() {
        //given
        Gung gung = Gung.generateInitialGung(CampType.CHO).getFirst();
        Position destination = new Position(STANDARD.getX() + 1, STANDARD.getY());
        Gung otherPiece = Gung.from(destination);

        //when & then
        assertThatThrownBy(() -> gung.move(destination, List.of(), List.of(otherPiece)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }
}