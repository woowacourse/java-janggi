package domain.chesspiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.path.Path;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseTest {

    @Test
    @DisplayName("마의 이동 경로를 반환한다")
    void test1() {
        // given
        final ChessPosition chessPosition = new ChessPosition(4, 4);
        final Path expected = new Path(List.of(
                new ChessPosition(4, 5),
                new ChessPosition(5,6)
        ));

        //when
        final Horse horse = new Horse(ChessTeam.RED, chessPosition);
        final List<Path> coordinatePaths = horse.calculateCoordinatePaths(chessPosition);

        //then
        assertThat(coordinatePaths).contains(expected);
    }
}
