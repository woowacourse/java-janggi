package domain.piece.strategy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.piece.strategy.component.PalaceMoveRule;
import domain.position.Position;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SlidingMoveStrategyTest {

    private final MoveStrategy strategy = new SlidingMoveStrategy(new PalaceMoveRule());


    @Test
    @DisplayName("같은 Row이면 목적지까지의 경로를 반환해야 한다")
    void findMovablePath_success_same_row() {
        // given
        Position start = Position.of(1, 1);
        Position destination = Position.of(1, 8);

        // when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        int expect = destination.getColumn().value() - start.getColumn().value() - 1;
        Assertions.assertThat(movablePath.size()).isEqualTo(expect);
        Assertions.assertThat(movablePath).contains(
                Position.of(1, 2),
                Position.of(1, 3),
                Position.of(1, 4),
                Position.of(1, 5),
                Position.of(1, 6),
                Position.of(1, 7)
        );
    }

    @Test
    @DisplayName("같은 Row이면 목적지까지의 경로를 반환해야 한다")
    void findMovablePath_success_same_column() {
        // given
        Position start = Position.of(1, 1);
        Position destination = Position.of(8, 1);

        // when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        int expect = destination.getRow().value() - start.getRow().value() - 1;
        Assertions.assertThat(movablePath.size()).isEqualTo(expect);
        Assertions.assertThat(movablePath).contains(
                Position.of(2, 1),
                Position.of(3, 1),
                Position.of(4, 1),
                Position.of(5, 1),
                Position.of(6, 1),
                Position.of(7, 1)
        );
    }

    @Test
    @DisplayName("목적지까지 이동한 가능한 경로가 없는 경우 예외가 발상해야 한다")
    void findMovablePath_fail_now_same_row_and_column() {
        // given
        Position start = Position.of(1, 2);
        Position destination = Position.of(3, 4);

        Assertions.assertThatThrownBy(() -> strategy.findMovablePath(start, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MoveStrategyErrorMessage.NOT_EXIST_MOVABLE_PATH.getMessage());
    }

    @Test
    @DisplayName("궁성 외부에서 내부로 이동 가능하다")
    void can_go_in_palace() {
        //given
        int expectSize = 0;
        Position start = Position.of(1, 3);
        Position destination = Position.of(1, 4);

        //when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        assertThat(movablePath.size()).isEqualTo(expectSize);
    }

    @Test
    @DisplayName("궁성 내부에서 대각선으로 이동 가능하다")
    void can_diagonal_go_in_palace() {
        //given
        int expectSize = 0;
        Position start = Position.of(1, 4);
        Position destination = Position.of(2, 5);

        //when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        assertThat(movablePath.size()).isEqualTo(expectSize);
    }
}
