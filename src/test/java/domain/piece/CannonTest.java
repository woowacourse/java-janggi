package domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Position;
import domain.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    @DisplayName("포의 이동 경로를 계산할 수 있다")
    @Test
    void test() {
        // given
        Cannon cannon = new Cannon(Team.RED);
        Position startPosition = new Position(3, 2);
        Position targetPosition = new Position(6, 2);
        // when
        List<Move> moves = cannon.calculatePath(startPosition, targetPosition);
        // then
        List<Move> expected = List.of(Move.BACK, Move.BACK, Move.BACK);
        Assertions.assertThat(moves).isEqualTo(expected);
    }

    @DisplayName("동일한 위치로 움직일 경우 예외를 발생시킨다")
    @Test
    void test2() {
        Cannon cannon = new Cannon(Team.RED);

        assertThatThrownBy(() -> cannon.calculatePath(new Position(1, 1), new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("말을 움직여 주세요");
    }
}