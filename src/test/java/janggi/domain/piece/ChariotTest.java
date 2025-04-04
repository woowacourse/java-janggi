package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import janggi.domain.piece.impl.Chariot;
import janggi.domain.piece.impl.None;
import janggi.domain.piece.impl.Soldier;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotTest {
    Map<Position, Piece> map;
    Chariot chariot = new Chariot(Team.BLUE);
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
        Position afterPosition = new Position(5, 8);

        assertThatCode(() ->
                chariot.getMovableValidator(beforePosition, afterPosition)
                        .accept(new Pieces(map))).doesNotThrowAnyException();
    }

    @DisplayName("차의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,7", "4,4"})
    @ParameterizedTest
    void move2(final int x, final int y) {
        Position afterPosition = new Position(x, y);

        assertThatThrownBy(() -> chariot.getMovableValidator(beforePosition, afterPosition).accept(new Pieces(map)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("차의 초기 위치와 이동 위치 사이에 말이 존재하는 경우 예외를 던진다.")
    @Test
    void move3() {
        Soldier otherSoldier = new Soldier(Team.BLUE);
        Position afterPosition = new Position(2, 5);
        map.put(new Position(3, 5), otherSoldier);

        assertThatThrownBy(() -> chariot.getMovableValidator(beforePosition, afterPosition).accept(new Pieces(map)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("원래 위치로 이동하려 하는 경우 예외를 던진다.")
    @Test
    void move33() {
        assertThatThrownBy(() -> chariot.getMovableValidator(beforePosition, beforePosition).accept(new Pieces(map)))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("차의 이동 위치에 같은 편 기물이 있으면 예외를 던진다")
    @Test
    void move4() {
        Soldier otherSoldier = new Soldier(Team.BLUE);
        Position afterPosition = new Position(2, 5);
        map.put(afterPosition, otherSoldier);

        assertThatThrownBy(() -> chariot.getMovableValidator(beforePosition, afterPosition).accept(new Pieces(map)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("차는 궁성 내에서 대각선으로 이동이 가능하다.")
    @Test
    void palaceMove() {
        Position palaceBeforePosition = new Position(8, 5);
        Position afterPosition = new Position(10, 7);

        assertThatCode(() ->
                chariot.getPalaceMovableValidator(palaceBeforePosition, afterPosition).accept(new Pieces(map)))
                .doesNotThrowAnyException();
    }

    @DisplayName("차는 궁성 내 대각선 이동 중 경로에 기물이 있으면 예외를 던진다.")
    @Test
    void palaceMoveWithObstacleShouldFail() {
        Position palaceBeforePosition = new Position(8, 5);
        Position between = new Position(9, 6); // 중간 경로
        Position afterPosition = new Position(10, 7);

        map.put(between, new Soldier(Team.RED));

        assertThatThrownBy(() ->
                chariot.getPalaceMovableValidator(palaceBeforePosition, afterPosition).accept(new Pieces(map))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("불가능한 이동입니다");
    }

    @DisplayName("궁성 내 대각선 이동이 아닌 잘못된 움직임이면 예외를 던진다.")
    @Test
    void invalidPalaceMoveShouldFail() {
        Position palaceBeforePosition = new Position(8, 5);
        Position afterPosition = new Position(9, 7);

        assertThatThrownBy(() ->
                chariot.getPalaceMovableValidator(palaceBeforePosition, afterPosition).accept(new Pieces(map))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
