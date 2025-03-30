package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Team;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GuardTest {

    Map<Position, Piece> pieces;

    @BeforeEach
    void setUp() {
        pieces = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                pieces.put(new Position(i, j), new None(new Position(i, j)));
            }
        }
    }

    @DisplayName("이동 위치 값을 입력 받아 이동한다.")
    @Test
    void move() {
        Guard guard = new Guard(new Position(8, 4), Team.BLUE);
        Position positionToMove = new Position(9, 4);
        Piece movedGuard = guard.move(pieces, positionToMove);
        assertThat(movedGuard.getPosition()).isEqualTo(positionToMove);
    }

    @DisplayName("사의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,5", "5,7"})
    @ParameterizedTest
    void move2(int x, int y) {
        Guard guard = new Guard(new Position(9, 5), Team.BLUE);
        Position positionToMove = new Position(x, y);
        assertThatThrownBy(() -> guard.move(pieces, positionToMove))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("사는 궁성 내부에서만 이동이 가능하다")
    @Test
    void move3() {
        Guard guard = new Guard(new Position(8, 4), Team.BLUE);
        pieces.put(guard.getPosition(), guard);
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> guard.move(pieces, new Position(7, 4)))
                .isInstanceOf(IllegalArgumentException.class),
            () -> assertThatThrownBy(() -> guard.move(pieces, new Position(8, 3)))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @DisplayName("사는 궁선 내 간선을 통해서 이동이 가능하다")
    @Test
    void move4() {
        Guard guard = new Guard(new Position(1, 4), Team.RED);
        Position afterPosition = new Position(2, 5);
        Piece movedGuard = guard.move(pieces, afterPosition);
        assertThat(movedGuard.getPosition()).isEqualTo(afterPosition);
    }
}
