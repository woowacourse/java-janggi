package domain.chesspiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.path.Path;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @DisplayName("상의 이동 경로를 계산한다")
    @Test
    void test1() {
        // given
        final ChessPosition chessPosition = new ChessPosition(4, 4);
        final Path expected = new Path(List.of(
                new ChessPosition(4, 5),
                new ChessPosition(5,6),
                new ChessPosition(6,7)
        ));

        //when
        final Elephant elephant = new Elephant(ChessTeam.RED, chessPosition);
        final List<Path> coordinatePaths = elephant.calculateCoordinatePaths(chessPosition);

        //then
        assertThat(coordinatePaths).contains(expected);
    }
}
