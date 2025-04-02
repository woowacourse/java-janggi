package janggi.domain.position;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {

    @Test
    void 경로_내부에_위치가_포함되어_있다면_TRUE를_반환한다() {
        // Given
        Position position = new Position(1, 1);
        Position targetPosition = new Position(2, 2);
        Path path = new Path(List.of(position, targetPosition));

        // When & Then
        assertThat(path.hasPosition(targetPosition))
                .isTrue();
    }

    @Test
    void 경로_내부에_위치가_포함되어_있지_않다면_FALSE를_반환한다() {
        // Given
        Position position = new Position(1, 1);
        Position targetPosition = new Position(2, 2);
        Path path = new Path(List.of(position));

        // When & Then
        assertThat(path.hasPosition(targetPosition))
                .isFalse();
    }
}
