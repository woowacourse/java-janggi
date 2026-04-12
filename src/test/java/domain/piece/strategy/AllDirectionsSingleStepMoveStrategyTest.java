package domain.piece.strategy;

import domain.position.Position;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AllDirectionsSingleStepMoveStrategyTest {
    private static final int EXPECT_SIZE = 1;

    @Test
    void 움직일_수_있다면_경로를_반환한다_전진() {
        // given
        Position start = Position.of(2, 9);
        Position destination = Position.of(3, 9);
        MoveStrategy strategy = new AllDirectionsSingleStepMoveStrategy();

        // when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    void 움직일_수_있다면_경로를_반환한다_후진() {
        // given
        Position start = Position.of(2, 9);
        Position destination = Position.of(1, 9);
        MoveStrategy strategy = new AllDirectionsSingleStepMoveStrategy();

        // when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    void 움직일_수_있다면_경로를_반환한다_우측() {
        // given
        Position start = Position.of(2, 8);
        Position destination = Position.of(2, 9);
        MoveStrategy strategy = new AllDirectionsSingleStepMoveStrategy();

        // when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    void 움직일_수_있다면_경로를_반환한다_좌측() {
        // given
        Position start = Position.of(2, 9);
        Position destination = Position.of(2, 8);
        MoveStrategy strategy = new AllDirectionsSingleStepMoveStrategy();

        // when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    void 움직일_수_있다면_경로를_반환한다_좌상단() {
        // given
        Position start = Position.of(2, 5);
        Position destination = Position.of(1, 4);
        MoveStrategy strategy = new AllDirectionsSingleStepMoveStrategy();

        // when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    void 움직일_수_있다면_경로를_반환한다_좌하단() {
        // given
        Position start = Position.of(2, 5);
        Position destination = Position.of(3, 4);
        MoveStrategy strategy = new AllDirectionsSingleStepMoveStrategy();

        // when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    void 움직일_수_있다면_경로를_반환한다_우상단() {
        // given
        Position start = Position.of(2, 5);
        Position destination = Position.of(1, 6);
        MoveStrategy strategy = new AllDirectionsSingleStepMoveStrategy();

        // when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    void 움직일_수_있다면_경로를_반환한다_우하단() {
        // given
        Position start = Position.of(2, 5);
        Position destination = Position.of(3, 6);
        MoveStrategy strategy = new AllDirectionsSingleStepMoveStrategy();

        // when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    void 잘못된_위치가_제공되면_예외가_발상해야_한다() {
        // given
        Position start = Position.of(2, 9);
        Position destination = Position.of(4, 9);
        MoveStrategy strategy = new AllDirectionsSingleStepMoveStrategy();

        // when & then
        Assertions.assertThatThrownBy(() -> strategy.findMovablePath(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }
}