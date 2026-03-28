package domain.piece.strategy;

import domain.position.Position;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class JolMoveStrategyTest {

    private static final int EXPECT_SIZE = 0;

    @Test
    void 움직일_수_있다면_경로를_반환한다_전진() {
        // given
        Position start = Position.of(2, 10);
        Position destination = Position.of(3, 10);

        MoveStrategy strategy = new JolMoveStrategy();
        List<Position> movablePath = strategy.findMovablePath(start, destination);
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    void 움직일_수_있다면_경로를_반환한다_우측() {
        // given
        Position start = Position.of(2, 9);
        Position destination = Position.of(2, 10);

        MoveStrategy strategy = new JolMoveStrategy();
        List<Position> movablePath = strategy.findMovablePath(start, destination);
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    void 움직일_수_있다면_경로를_반환한다_좌측() {
        // given
        Position start = Position.of(2, 10);
        Position destination = Position.of(2, 9);

        MoveStrategy strategy = new JolMoveStrategy();
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    void 잘못된_위치가_제공되면_예외가_발상해야_한다() {
        // given
        Position start = Position.of(2, 9);
        Position destination = Position.of(4, 9);

        MoveStrategy strategy = new JolMoveStrategy();

        Assertions.assertThatThrownBy(() -> strategy.findMovablePath(start, destination)).isInstanceOf(
                IllegalArgumentException.class);
    }

}
