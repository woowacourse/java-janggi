package domain.chesspiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.path.Path;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PawnTest {

    @Test
    @DisplayName("폰이 이동 가능한 경로를 반환한다")
    void test1() {
        // given
        final ChessPosition chessPosition = new ChessPosition(4, 4);
        final Path expected = new Path(List.of(
                new ChessPosition(3, 4)
        ));

        //when
        final Pawn pawn = new Pawn(ChessTeam.BLUE, chessPosition);
        final List<Path> coordinatePaths = pawn.calculateCoordinatePaths(chessPosition);

        //then
        assertThat(coordinatePaths).contains(expected);
    }
}
