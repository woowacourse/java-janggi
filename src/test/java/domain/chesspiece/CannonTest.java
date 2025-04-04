package domain.chesspiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.path.Path;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    @Test
    @DisplayName("포의 이동 경로를 계산한다")
    void test1() {
        //given
        final ChessPosition chessPosition = new ChessPosition(5, 3);
        final Path expected = new Path(List.of(
                new ChessPosition(5, 1),
                new ChessPosition(5, 2),
                new ChessPosition(5, 0))
        );

        //when
        final Cannon cannon = new Cannon(ChessTeam.RED, chessPosition);
        final List<Path> coordinatePaths = cannon.calculateCoordinatePaths(chessPosition);

        //then
        assertThat(coordinatePaths).contains(expected);
    }

}
