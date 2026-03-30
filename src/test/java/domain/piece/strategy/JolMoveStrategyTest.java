package domain.piece.strategy;

import common.ErrorMessage;
import domain.position.Position;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JolMoveStrategyTest {

    private static final int EXPECT_SIZE = 0;

    @Test
    @DisplayName("움직일 수 있다면 경로를 반환한다 : 전진")
    void findMovablePath_success_front() {
        // given
        Position start = Position.of(2, 10);
        Position destination = Position.of(3, 10);

        MoveStrategy strategy = new JolMoveStrategy();
        List<Position> movablePath = strategy.findMovablePath(start, destination);
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    @DisplayName("움직일 수 있다면 경로를 반환한다 : 우측")
    void findMovablePath_success_right() {
        // given
        Position start = Position.of(2, 9);
        Position destination = Position.of(2, 10);

        MoveStrategy strategy = new JolMoveStrategy();
        List<Position> movablePath = strategy.findMovablePath(start, destination);
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    @DisplayName("움직일 수 있다면 경로를 반환한다 : 좌측")
    void findMovablePath_success_left() {
        // given
        Position start = Position.of(2, 10);
        Position destination = Position.of(2, 9);

        MoveStrategy strategy = new JolMoveStrategy();
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    @DisplayName("잘못된 위치가 제공되면 예외가 발상해야 한다")
    void findMovablePath_fail_cause_of_incorrect_position() {
        // given
        Position start = Position.of(2, 9);
        Position destination = Position.of(4, 9);

        MoveStrategy strategy = new JolMoveStrategy();

        Assertions.assertThatThrownBy(() -> strategy.findMovablePath(start, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_POS_INPUT.getMessage());
    }

}
