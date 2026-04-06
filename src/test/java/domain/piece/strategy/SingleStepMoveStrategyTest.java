package domain.piece.strategy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.piece.strategy.component.PalaceMoveRule;
import domain.position.Position;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SingleStepMoveStrategyTest {

    private static final int EXPECT_SIZE = 0;

    private final MoveStrategy strategy = new SingleStepMoveStrategy(new PalaceMoveRule());


    @Test
    @DisplayName("움직일 수 있다면 경로를 반환한다 : 전진 ")
    void findMovablePath_success_front() {
        // given
        Position start = Position.of(2, 10);
        Position destination = Position.of(3, 10);

        List<Position> movablePath = strategy.findMovablePath(start, destination);
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    @DisplayName("움직일 수 있다면 경로를 반환한다 : 후진")
    void findMovablePath_success_back() {
        // given
        Position start = Position.of(2, 10);
        Position destination = Position.of(1, 10);

        List<Position> movablePath = strategy.findMovablePath(start, destination);
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    @DisplayName("움직일 수 있다면 경로를 반환한다 : 우측")
    void findMovablePath_success_right() {
        // given
        Position start = Position.of(2, 9);
        Position destination = Position.of(2, 10);

        List<Position> movablePath = strategy.findMovablePath(start, destination);
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    @DisplayName("움직일 수 있다면 경로를 반환한다 : 좌측")
    void findMovablePath_success_left() {
        // given
        Position start = Position.of(2, 10);
        Position destination = Position.of(2, 9);

        List<Position> movablePath = strategy.findMovablePath(start, destination);
        Assertions.assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    @DisplayName("목적지까지 이동한 가능한 경로가 없는 경우 예외가 발상해야 한다")
    void findMovablePath_fail_cause_of_incorrect_position() {
        // given
        Position start = Position.of(2, 9);
        Position destination = Position.of(4, 9);

        Assertions.assertThatThrownBy(() -> strategy.findMovablePath(start, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MoveStrategyErrorMessage.NOT_EXIST_MOVABLE_PATH.getMessage());
    }


    @Test
    @DisplayName("궁성 외부에서 내부로 이동 가능하다")
    void can_go_in_palace() {
        //given
        Position start = Position.of(1, 3);
        Position destination = Position.of(1, 4);

        //when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }

    @Test
    @DisplayName("궁성 내부에서 대각선으로 이동 가능하다")
    void can_diagonal_go_in_palace() {
        //given
        Position start = Position.of(1, 4);
        Position destination = Position.of(2, 5);

        //when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        assertThat(movablePath.size()).isEqualTo(EXPECT_SIZE);
    }
}
