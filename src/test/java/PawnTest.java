import domain.ChessPosition;
import domain.ChessTeam;
import domain.Path;
import domain.Pawn;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PawnTest {

    @Test
    @DisplayName("폰이 이동 가능한 경로를 반환한다")
    void test1() {
        //given
        final ChessPosition chessPosition = new ChessPosition(0, 0);
        final List<Path> expectPaths = List.of(
                new Path(List.of(new ChessPosition(1,0))),
                new Path(List.of(new ChessPosition(0,1)))
        );

        //when
        final Pawn pawn = new Pawn(ChessTeam.RED);
        final List<Path> actualPaths = pawn.getAvailablePaths(chessPosition);

        //then
        SoftAssertions.assertSoftly((softly) -> {
            softly.assertThat(actualPaths).hasSize(2);
            softly.assertThat(actualPaths.get(0).getPath()).containsAll(expectPaths.get(0).getPath());
            softly.assertThat(actualPaths.get(1).getPath()).containsAll(expectPaths.get(1).getPath());
        });
    }
}
