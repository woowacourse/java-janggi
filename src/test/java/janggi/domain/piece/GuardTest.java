package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GuardTest {
    @DisplayName("이동 위치 값을 입력 받아 이동한다.")
    @Test
    void move() {
        Guard guard = new Guard(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(5, 6);
        Map<Position, Piece> pieces = new HashMap<>();

        Guard movedGuard = guard.move(pieces, positionToMove);

        assertThat(movedGuard.getPosition()).isEqualTo(positionToMove);
    }

    @DisplayName("사의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,5", "5,7"})
    @ParameterizedTest
    void move2(int x, int y) {
        Guard guard = new Guard(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(x, y);
        Map<Position, Piece> pieces = new HashMap<>();
        assertThatThrownBy(() -> guard.move(pieces, positionToMove))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("사의 이동 위치에 같은 편 말이 있으면 예외를 던진다")
    @Test
    void move4() {
        Guard guard = new Guard(new Position(5, 5), TeamType.BLUE);
        Map<Position, Piece> pieces = new HashMap<>();
        Soldier otherSoldier = new Soldier(new Position(4, 5), TeamType.BLUE);
        pieces.put(otherSoldier.getPosition(), otherSoldier);
        assertThatThrownBy(() ->
                guard.move(pieces, otherSoldier.getPosition()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}