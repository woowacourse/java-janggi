import domain.Path;
import domain.ChessPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {
    @DisplayName("목적지의 위치를 알려줄 수 있다.")
    @Test
    void getDestination() {
        // given
        ChessPosition destination = new ChessPosition(0, 1);
        Path path = new Path(List.of(
                new ChessPosition(0, 0),
                new ChessPosition(1, 0),
                destination
        ));

        // when
        ChessPosition chessPosition = path.getDestination();

        // then
        assertThat(chessPosition).isEqualTo(destination);
    }
}
