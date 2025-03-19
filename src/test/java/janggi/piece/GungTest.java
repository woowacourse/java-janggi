package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

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

    @DisplayName("장기말의 이동 규칙에 어긋난 경우")
    @ParameterizedTest
    @MethodSource()
    void test1(Position position, boolean expectedResult) {
        //given
        Gung gung = Gung.generateInitialGung(CampType.CHO).getFirst();

        //when & then
        assertThat(gung.ableToMove(position, List.of(), List.of()))
                .isEqualTo(expectedResult);
    }

    static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(new Position(STANDARD.getX() + 1, STANDARD.getY()), true),
                Arguments.of(new Position(STANDARD.getX() - 1, STANDARD.getY()), true),
                Arguments.of(new Position(STANDARD.getX(), STANDARD.getY() + 1), true),
                Arguments.of(new Position(STANDARD.getX(), STANDARD.getY() - 1), true),
                Arguments.of(new Position(STANDARD.getX() + 2, STANDARD.getY()), false),
                Arguments.of(new Position(STANDARD.getX() - 2, STANDARD.getY()), false),
                Arguments.of(new Position(STANDARD.getX(), STANDARD.getY() + 2), false),
                Arguments.of(new Position(STANDARD.getX(), STANDARD.getY() - 2), false)
        );
    }

    @DisplayName("아군 장기말이 장애물일 경우 해당 위치로 이동이 불가능하다.")
    @Test
    void test2() {
        //given
        Gung gung = Gung.generateInitialGung(CampType.CHO).getFirst();
        Position destination = new Position(STANDARD.getX() + 1, STANDARD.getY());
        Gung otherPiece = Gung.from(destination);

        boolean actualCanMove = gung.ableToMove(destination, List.of(), List.of(otherPiece));

        //when & then
        assertThat(actualCanMove).isFalse();
    }
}