package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import janggi.domain.piece.impl.Guard;
import janggi.domain.piece.impl.None;
import janggi.domain.piece.impl.Soldier;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GuardTest {
    Map<Position, Piece> map;
    Guard guard = new Guard(Team.RED);
    Position beforePosition = new Position(9, 5);

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
        Position afterPosition = new Position(9, 4);

        assertThatCode(() ->
                guard.getPalaceMovableValidator(beforePosition, afterPosition)
                        .accept(new Pieces(map))).doesNotThrowAnyException();
    }

    @DisplayName("사의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,5", "5,7"})
    @ParameterizedTest
    void move2(final int x, final int y) {
        Position afterPosition = new Position(x, y);

        assertThatThrownBy(() -> guard.getPalaceMovableValidator(beforePosition, afterPosition).accept(new Pieces(map)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("사의 이동 위치에 같은 편 기물이 있으면 예외를 던진다")
    @Test
    void move4() {
        Soldier otherSoldier = new Soldier(Team.RED);
        Position afterPosition = new Position(9, 6);
        map.put(afterPosition, otherSoldier);

        assertThatThrownBy(() -> guard.getPalaceMovableValidator(beforePosition, afterPosition).accept(new Pieces(map)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("사는 이전 위치와 이후 위치가 모두 궁성 내에 있지 않은 경우 예외를 발생시킨다.")
    @Test
    void palaceMove() {
        Position afterPalacePosition = new Position(8, 4);

        assertThatThrownBy(
                () -> guard.getMovableValidator(beforePosition, afterPalacePosition).accept(new Pieces(map)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사는 궁성 내에서만 이동할 수 있습니다.");
    }
}
