package janggi.domain.piece.strategy;

import janggi.domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectionTest {

    @DisplayName("4가지 방향으로 이동한 Position 객체를 정상적으로 반환하는지 테스트")
    @Test
    void next_ReceiveCurrentPosition_ReturnFindNextPositionByDirectionPosition() {
        int currentRow = 5;
        int currentCol = 5;
        Position currentPosition = Position.of(currentRow, currentCol);
        for (Direction direction : Direction.linear()) {
            Optional<Position> position = direction.findNextPosition(currentPosition);
            Position expectedPosition = Position.of(currentRow + direction.directionRow(), currentCol + direction.directionColumn());
            assertThat(position).isPresent();
            assertThat(position.get()).isEqualTo(expectedPosition);
        }
    }
}
