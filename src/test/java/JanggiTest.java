import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class JanggiTest {
    public static Stream<Arguments> provideXY() {
        return Stream.of(
                Arguments.of(-1, 7),
                Arguments.of(2,10),
                Arguments.of(9, -1)
        );
    }

    @DisplayName("x와 y 좌표를 가지고 있는 점을 생성한다.")
    @Test
    void createPosition() {
        // given
        int x = 1;
        int y = 2;

        // when // then
        assertThatCode(() -> new Dot(x, y))
                .doesNotThrowAnyException();

    }

    @DisplayName("x 좌표가 0부터 8까지의 범위를 가진다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8})
    void xHasBoundary(int x) {
        // given
        int y = 2;

        // when // then
        assertThatCode(() -> new Dot(x, y))
                .doesNotThrowAnyException();
    }

    @DisplayName("y 좌표가 0부터 9까지의 범위를 가진다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    void yHasBoundary(int y) {
        // given
        int x = 2;

        // when // then
        assertThatCode(() -> new Dot(x, y))
                .doesNotThrowAnyException();
    }

    @DisplayName("범위를 벗어난 점을 생성할 경우 예외 처리한다.")
    @ParameterizedTest
    @MethodSource("provideXY")
    void validateDotRange(int x, int y) {

        // when // then
        assertThatCode(() -> new Dot(x, y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("x와 y의 정해진 범위에 맞게 점을 생성 해둔다.")
    @Test
    void createDotsCache() {
        // given
        Dot dotA = Dot.of(1, 1);
        Dot dotB = Dot.of(1, 1);

        // when
        boolean actual = dotA == dotB;

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("각각의 기물은 나라를 가진다.")
    @Test
    void pieceHasDynasty() {
        // given
        final var dynasty = Dynasty.HAN;

        // when // then
        assertThatCode(() -> new Chariot(dynasty))
                .doesNotThrowAnyException();
    }

    @DisplayName("나라는 한나라(Han)와 초나라(Cho)로 나뉜다.")
    @Test
    void dynastyDivideHanCho() {
        // given
        Dynasty[] dynasties = Dynasty.values();

        // when
        int actual = dynasties.length;

        // then
        assertThat(actual).isEqualTo(2);
    }

    @DisplayName("차는 목적지로 가는 경로를 구할 수 있다.")
    @Test
    void chariotCanGetRoute() {
        // given
        Dot origin = Dot.of(1, 1);
        Dot destination = Dot.of(1, 3);
        Chariot chariot = new Chariot(Dynasty.HAN);

        // when
        List<Dot> actual = chariot.getRoute(origin, destination);

        List<Dot> expected = List.of(Dot.of(1, 2));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("차는 이동 경로에 어떤 말도 없다면 이동 가능하다")
    @Test
    void chariotJudgeMovable() {
        // given
        Map<Dot, Chariot> routesWithPiece = new LinkedHashMap<>();
        Chariot chariot = new Chariot(Dynasty.HAN);

        routesWithPiece.put(Dot.of(1,2), null);
        routesWithPiece.put(Dot.of(1,3), null);

        // when
        boolean actual = chariot.canMove(routesWithPiece, null);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("차는 이동 경로에 기물이 존재한다면 이동할 수 없다")
    @Test
    void chariotJudgeMovable2() {
        // given
        Map<Dot, Chariot> routesWithPiece = new LinkedHashMap<>();
        Chariot chariot = new Chariot(Dynasty.HAN);

        routesWithPiece.put(Dot.of(1,2), null);
        routesWithPiece.put(Dot.of(1,3), new Chariot(Dynasty.HAN));

        // when
        boolean actual = chariot.canMove(routesWithPiece, null);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("차는 목적지에 같은 나라의 기물이 존재한다면 이동할 수 없다")
    @Test
    void chariotJudgeMovable3() {
        // given
        Map<Dot, Chariot> routesWithPiece = new LinkedHashMap<>();
        Chariot chariot = new Chariot(Dynasty.HAN);

        routesWithPiece.put(Dot.of(1,2), null);
        routesWithPiece.put(Dot.of(1,3), null);

        // when
        boolean actual = chariot.canMove(routesWithPiece, new Chariot(Dynasty.HAN));

        // then
        assertThat(actual).isFalse();
    }




}
