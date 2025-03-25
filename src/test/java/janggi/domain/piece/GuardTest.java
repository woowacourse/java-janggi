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

class GuardTest {
    Map<Position, Piece> board;
    Position beforePosition = new Position(5, 5);

    @BeforeEach
    void setUp() {
        board = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                board.put(new Position(i, j), new None());
            }
        }
    }

    @DisplayName("이동 위치 값을 입력 받아 이동한다.")
    @Test
    void move() {
        Guard guard = new Guard(Team.BLUE);
        Position afterPosition = new Position(5, 6);

        assertThatCode(() ->
                guard.getMovableValidator(beforePosition, afterPosition).accept(board)).doesNotThrowAnyException();
    }

    @DisplayName("사의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,5", "5,7"})
    @ParameterizedTest
    void move2(int x, int y) {
        Guard guard = new Guard(Team.BLUE);
        Position afterPosition = new Position(x, y);

        assertThatThrownBy(() -> guard.getMovableValidator(beforePosition, afterPosition).accept(board))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("사의 이동 위치에 같은 편 기물이 있으면 예외를 던진다")
    @Test
    void move4() {
        Guard guard = new Guard(Team.BLUE);
        Soldier otherSoldier = new Soldier(Team.BLUE);
        Position afterPosition = new Position(5, 6);
        board.put(afterPosition, otherSoldier);

        assertThatThrownBy(() -> guard.getMovableValidator(beforePosition, afterPosition).accept(board))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
