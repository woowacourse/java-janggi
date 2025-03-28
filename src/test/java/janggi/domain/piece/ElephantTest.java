package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ElephantTest {
    Map<Position, Piece> map;
    Position beforePosition = new Position(5, 5);

    @BeforeEach
    void setUp() {
        map = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                map.put(new Position(i, j), new None());
            }
        }
    }

    @DisplayName("이동 위치 값을 입력 받아 이동한다.")
    @Test
    void move() {
        Elephant elephant = new Elephant(Team.BLUE);
        Position afterPosition = new Position(2, 3);

        assertThatCode(() ->
                elephant.getMovableValidator(beforePosition, afterPosition)
                        .accept(new Pieces(map))).doesNotThrowAnyException();
    }

    @DisplayName("상의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,7", "4,4", "6,5"})
    @ParameterizedTest
    void move2(final int x, final int y) {
        Elephant elephant = new Elephant(Team.BLUE);
        Position afterPosition = new Position(x, y);

        assertThatThrownBy(() -> elephant.getMovableValidator(beforePosition, afterPosition).accept(new Pieces(map)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상의 초기 위치와 이동 위치 사이에 기물이 존재하는 경우 예외를 던진다.")
    @Test
    void move3() {
        Elephant elephant = new Elephant(Team.BLUE);
        Soldier otherSoldier = new Soldier(Team.BLUE);
        Position afterPosition = new Position(2, 3);
        Position betweenPosition = new Position(4, 5);
        map.put(betweenPosition, otherSoldier);

        assertThatThrownBy(() -> elephant.getMovableValidator(beforePosition, afterPosition).accept(new Pieces(map)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상의 이동 위치에 같은 편 기물이 있으면 예외를 던진다")
    @Test
    void move4() {
        Elephant elephant = new Elephant(Team.BLUE);
        Soldier otherSoldier = new Soldier(Team.BLUE);
        Position afterPosition = new Position(2, 3);
        map.put(afterPosition, otherSoldier);

        assertThatThrownBy(() -> elephant.getMovableValidator(beforePosition, afterPosition).accept(new Pieces(map)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상의 모든 이동 경로가 가능하다")
    @CsvSource(value = {
            "2,3",
            "2,7",
            "8,7",
            "8,3",
            "7,2",
            "7,8",
            "3,2",
            "3,8",
    })
    @ParameterizedTest
    void move6(final int x, final int y) {
        Elephant elephant = new Elephant(Team.BLUE);
        Position afterPosition = new Position(x, y);

        assertThatCode(() ->
                elephant.getMovableValidator(beforePosition, afterPosition)
                        .accept(new Pieces(map))).doesNotThrowAnyException();
    }
}
