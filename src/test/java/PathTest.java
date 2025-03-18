import domain.Path;
import domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {
    @DisplayName("목적지의 위치를 알려줄 수 있다.")
    @Test
    void getDestination() {
        // given
        Position destination = new Position(0, 1);
        Path path = new Path(List.of(
                new Position(0, 0),
                new Position(1, 0),
                destination
        ));

        // when
        Position position = path.getDestination();

        // then
        assertThat(position).isEqualTo(destination);
    }
}
