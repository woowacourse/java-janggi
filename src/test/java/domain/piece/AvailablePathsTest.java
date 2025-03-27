package domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Coordinate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AvailablePathsTest {

    @DisplayName("이동 불가능한 위치라면 예외를 던진다.")
    @Test
    void pathsTest() {
        AvailablePaths availablePaths = new AvailablePaths(
                List.of(new Coordinate(1, 1), new Coordinate(1, 2))
        );

        Coordinate to = new Coordinate(1, 3);

        assertThatThrownBy(() -> availablePaths.canMove(to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
